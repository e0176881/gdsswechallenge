package com.orson.swechallenge.controller;

import com.orson.swechallenge.constants.CommonConfig;
import com.orson.swechallenge.dto.UserDetailDTO;
import com.orson.swechallenge.entity.UserDetail;
import com.orson.swechallenge.exceptionhandling.ErrorResponse;
import com.orson.swechallenge.exceptionhandling.UploadFailureResponse;
import com.orson.swechallenge.helper.ConstraintsCheck;
import com.orson.swechallenge.response.UploadResponse;
import com.orson.swechallenge.response.UserDetailResponse;
import com.orson.swechallenge.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class UserDetailController {

    @Autowired
    UserDetailService userDetailService;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUserDetails(
            @RequestParam(defaultValue = CommonConfig.DEFAULT_MIN_SALARY) String min,
            @RequestParam(defaultValue = CommonConfig.DEFAULT_MAX_SALARY) String max,
            @RequestParam(defaultValue = CommonConfig.DEFAULT_OFFSET) String offset,
            @RequestParam(defaultValue = CommonConfig.DEFAULT_MYSQL_MAX_LIMIT) String limit,
            @RequestParam(defaultValue = CommonConfig.DEFAULT_SORT) String sort) {

        min = ConstraintsCheck.nullToDefault(min, CommonConfig.DEFAULT_MIN_SALARY);
        max = ConstraintsCheck.nullToDefault(max, CommonConfig.DEFAULT_MAX_SALARY);
        offset = ConstraintsCheck.nullToDefault(offset, CommonConfig.DEFAULT_OFFSET);
        limit = ConstraintsCheck.nullToDefault(limit, CommonConfig.DEFAULT_MYSQL_MAX_LIMIT);
        sort = ConstraintsCheck.nullToDefault(sort, CommonConfig.DEFAULT_SORT);

        if (!ConstraintsCheck.checkIfValidSort(sort)) {
            ErrorResponse err = new ErrorResponse();
            err.setError("Invalid Sort Type");
            return new ResponseEntity<ErrorResponse>(err, HttpStatus.BAD_REQUEST);
        }

        if (ConstraintsCheck.checkIfValidArgumentType(min, max, offset, limit) != "Valid") {
            ErrorResponse err = new ErrorResponse();
            err.setError(ConstraintsCheck.checkIfValidArgumentType(min, max, offset, limit));
            return new ResponseEntity<ErrorResponse>(err, HttpStatus.BAD_REQUEST);
        }


        List<UserDetail> userDetailList = userDetailService.getUserDetails(min, max, offset, limit, sort);
        List<UserDetailDTO> userDetailDTOList = new ArrayList<UserDetailDTO>();
        userDetailList.stream().forEach(userDetail -> {
            userDetailDTOList.add(new UserDetailDTO(userDetail));
        });
        UserDetailResponse userDetailResponse = new UserDetailResponse();
        userDetailResponse.setResults(userDetailDTOList);
        return new ResponseEntity<>(userDetailResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadMultipart(@RequestParam("file") MultipartFile file) throws IOException {
        boolean success = userDetailService.uploadUserDetail(file.getInputStream());
        if (success) {
            UploadResponse uploadResponse = new UploadResponse();
            uploadResponse.setSuccess(1);
            return new ResponseEntity<UploadResponse>(uploadResponse, HttpStatus.OK);
        } else {
            UploadFailureResponse uploadFailureResponse = new UploadFailureResponse();
            uploadFailureResponse.setError("Invalid format");
            uploadFailureResponse.setFailure(0);
            return new ResponseEntity<UploadFailureResponse>(uploadFailureResponse, HttpStatus.BAD_REQUEST);
        }

    }
}

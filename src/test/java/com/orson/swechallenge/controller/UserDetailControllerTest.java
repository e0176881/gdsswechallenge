package com.orson.swechallenge.controller;

import com.orson.swechallenge.dto.UserDetailDTO;
import com.orson.swechallenge.entity.UserDetail;
import com.orson.swechallenge.exceptionhandling.ErrorResponse;
import com.orson.swechallenge.response.UserDetailResponse;
import com.orson.swechallenge.service.UserDetailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserDetailControllerTest {
    @InjectMocks
    private UserDetailController userDetailController;
    @Mock
    private UserDetailService userDetailService;
    private List<UserDetail> userDetailList;
    private List<UserDetail> salaryAsc_UserDetails;

    List<UserDetailDTO> userDetailDTOList = new ArrayList<UserDetailDTO>();
    @BeforeEach
    void setUp() {
        UserDetail userDetail1 = new UserDetail();
        userDetail1.setId(1);
        userDetail1.setName("john");
        userDetail1.setSalary(4000.0f);
        UserDetail userDetail2 = new UserDetail();
        userDetail2.setId(2);
        userDetail2.setName("john 2");
        userDetail2.setSalary(3000.0f);

        this.userDetailList = new ArrayList<>();
        this.userDetailList.add(userDetail1);
        this.userDetailList.add(userDetail2);
        this.salaryAsc_UserDetails = new ArrayList<>();
        this.salaryAsc_UserDetails.add(userDetail2);
        this.salaryAsc_UserDetails.add(userDetail1);

        UserDetailDTO userDTO1 = new UserDetailDTO(userDetail1);
        UserDetailDTO userDTO2 = new UserDetailDTO(userDetail2);
        userDetailDTOList.add(userDTO1);
        userDetailDTOList.add(userDTO2);

    }

    @Test
    public void getUserDetailWithoutSort() throws Exception{

        UserDetailResponse expectedUserDetailResponse = new UserDetailResponse();
        expectedUserDetailResponse.setResults(userDetailDTOList);

        when(userDetailService.getUserDetails("1000", "4000", "0", "2147483647", "")).thenReturn(userDetailList);
        ResponseEntity<UserDetailResponse> userDetailResponse = (ResponseEntity<UserDetailResponse>) userDetailController.getAllUserDetails("1000", "4000", "0", "2147483647", "");
        System.out.println(userDetailResponse.getBody());
        assertEquals(HttpStatus.OK, userDetailResponse.getStatusCode());
        assertThat(userDetailResponse.getBody().getResults())
                .isNotNull()
                .extracting("name",
                        "salary")
                .containsExactly(tuple("john", 4000.0f),
                        tuple("john 2", 3000.0f));

    }

    @Test
    public void getUserDetailValidSortName() throws Exception{

        UserDetailResponse expectedUserDetailResponse = new UserDetailResponse();
        expectedUserDetailResponse.setResults(userDetailDTOList);

        when(userDetailService.getUserDetails("1000", "4000", "0", "2147483647", "name")).thenReturn(userDetailList);
        ResponseEntity<UserDetailResponse> userDetailResponse = (ResponseEntity<UserDetailResponse>) userDetailController.getAllUserDetails("1000", "4000", "0", "2147483647", "name");
        assertEquals(HttpStatus.OK, userDetailResponse.getStatusCode());
        assertThat(userDetailResponse.getBody().getResults())
                .isNotNull()
                .extracting("name",
                        "salary")
                .containsExactly(tuple("john", 4000.0f),
                        tuple("john 2", 3000.0f));

    }

    @Test
    public void getUserDetailValidSortSalary() throws Exception{

        UserDetailResponse expectedUserDetailResponse = new UserDetailResponse();
        expectedUserDetailResponse.setResults(userDetailDTOList);


        when(userDetailService.getUserDetails("1000", "4000", "0", "2147483647", "salary")).thenReturn(salaryAsc_UserDetails);
        ResponseEntity<UserDetailResponse> userDetailResponse = (ResponseEntity<UserDetailResponse>) userDetailController.getAllUserDetails("1000", "4000", "0", "2147483647", "salary");
        assertEquals(HttpStatus.OK, userDetailResponse.getStatusCode());
        assertThat(userDetailResponse.getBody().getResults())
                .isNotNull()
                .extracting("name",
                        "salary")
                .containsExactly(tuple("john 2", 3000.0f),
                        tuple("john", 4000.0f));

    }

    @Test
    public void getUserDetailWithEmptyString() throws Exception{

        UserDetailResponse expectedUserDetailResponse = new UserDetailResponse();
        expectedUserDetailResponse.setResults(userDetailDTOList);

        when(userDetailService.getUserDetails("0.0", "4000.0", "0", "2147483647", "")).thenReturn(userDetailList);
        ResponseEntity<UserDetailResponse> userDetailResponse = (ResponseEntity<UserDetailResponse>) userDetailController.getAllUserDetails("", "", "", "", "");
        assertEquals(HttpStatus.OK, userDetailResponse.getStatusCode());
        assertThat(userDetailResponse.getBody().getResults())
                .isNotNull()
                .extracting("name",
                        "salary")
                .containsExactly(tuple("john", 4000.0f),
                        tuple("john 2", 3000.0f));

    }

    @Test
    public void getUserDetailWithNullValues() throws Exception{

        UserDetailResponse expectedUserDetailResponse = new UserDetailResponse();
        expectedUserDetailResponse.setResults(userDetailDTOList);

        when(userDetailService.getUserDetails("0.0", "4000.0", "0", "2147483647", "")).thenReturn(userDetailList);
        ResponseEntity<UserDetailResponse> userDetailResponse = (ResponseEntity<UserDetailResponse>) userDetailController.getAllUserDetails(null, null, null, null, null);
        assertEquals(HttpStatus.OK, userDetailResponse.getStatusCode());
        assertThat(userDetailResponse.getBody().getResults())
                .isNotNull()
                .extracting("name",
                        "salary")
                .containsExactly(tuple("john", 4000.0f),
                        tuple("john 2", 3000.0f));

    }

    @Test
    public void getUserDetailWithNullString() throws Exception{

        UserDetailResponse expectedUserDetailResponse = new UserDetailResponse();
        expectedUserDetailResponse.setResults(userDetailDTOList);

        when(userDetailService.getUserDetails("0.0", "4000.0", "0", "2147483647", "")).thenReturn(userDetailList);
        ResponseEntity<UserDetailResponse> userDetailResponse = (ResponseEntity<UserDetailResponse>) userDetailController.getAllUserDetails("null", "null", "null", "null", "null");
        assertEquals(HttpStatus.OK, userDetailResponse.getStatusCode());
        assertThat(userDetailResponse.getBody().getResults())
                .isNotNull()
                .extracting("name",
                        "salary")
                .containsExactly(tuple("john", 4000.0f),
                        tuple("john 2", 3000.0f));

    }



    @Test
    public void getUserDetailInValidSortName() throws Exception{

        UserDetailResponse expectedUserDetailResponse = new UserDetailResponse();
        expectedUserDetailResponse.setResults(userDetailDTOList);

        ResponseEntity<ErrorResponse> userDetailResponse = (ResponseEntity<ErrorResponse>) userDetailController.getAllUserDetails("1000", "4000", "0", "2147483647", "aaaa");
        assertEquals(HttpStatus.BAD_REQUEST, userDetailResponse.getStatusCode());
        assertThat(userDetailResponse.getBody())
                .isNotNull()
                .extracting("error")
                .isEqualTo("Invalid Sort Type");
    }

    @Test
    public void getUserDetailInValidInputMin() throws Exception{

        UserDetailResponse expectedUserDetailResponse = new UserDetailResponse();
        expectedUserDetailResponse.setResults(userDetailDTOList);

        ResponseEntity<ErrorResponse> userDetailResponse = (ResponseEntity<ErrorResponse>) userDetailController.getAllUserDetails("aaa", "4000", "0", "2147483647", "");
        assertEquals(HttpStatus.BAD_REQUEST, userDetailResponse.getStatusCode());
        assertThat(userDetailResponse.getBody())
                .isNotNull()
                .extracting("error")
                .isEqualTo("Invalid Input Type");
    }

    @Test
    public void getUserDetailInValidInputMax() throws Exception{

        UserDetailResponse expectedUserDetailResponse = new UserDetailResponse();
        expectedUserDetailResponse.setResults(userDetailDTOList);

        ResponseEntity<ErrorResponse> userDetailResponse = (ResponseEntity<ErrorResponse>) userDetailController.getAllUserDetails("1000", "4000.0.0", "0", "2147483647", "");
        assertEquals(HttpStatus.BAD_REQUEST, userDetailResponse.getStatusCode());
        assertThat(userDetailResponse.getBody())
                .isNotNull()
                .extracting("error")
                .isEqualTo("Invalid Input Type");
    }

    @Test
    public void getUserDetailInValidInputOffset() throws Exception{

        UserDetailResponse expectedUserDetailResponse = new UserDetailResponse();
        expectedUserDetailResponse.setResults(userDetailDTOList);

        ResponseEntity<ErrorResponse> userDetailResponse = (ResponseEntity<ErrorResponse>) userDetailController.getAllUserDetails("1000", "4000.0", "0.1", "2147483647", "");
        assertEquals(HttpStatus.BAD_REQUEST, userDetailResponse.getStatusCode());
        assertThat(userDetailResponse.getBody())
                .isNotNull()
                .extracting("error")
                .isEqualTo("Invalid Input Type");
    }

    @Test
    public void getUserDetailInValidInputLimit() throws Exception{

        UserDetailResponse expectedUserDetailResponse = new UserDetailResponse();
        expectedUserDetailResponse.setResults(userDetailDTOList);

        ResponseEntity<ErrorResponse> userDetailResponse = (ResponseEntity<ErrorResponse>) userDetailController.getAllUserDetails("1000", "4000.0", "0", "abc", "");
        assertEquals(HttpStatus.BAD_REQUEST, userDetailResponse.getStatusCode());
        assertThat(userDetailResponse.getBody())
                .isNotNull()
                .extracting("error")
                .isEqualTo("Invalid Input Type");
    }
}

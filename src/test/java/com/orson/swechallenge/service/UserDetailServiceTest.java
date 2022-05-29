package com.orson.swechallenge.service;

import com.orson.swechallenge.entity.UserDetail;
import com.orson.swechallenge.repository.UserDetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailServiceTest {

    @InjectMocks
    private UserDetailService userDetailService;

    @Mock
    private UserDetailRepository userDetailRepository;

    private List<UserDetail> expectedNoSortUserDetailList = new ArrayList<UserDetail>();
    private List<UserDetail> expectedSortByNameUserDetailList = new ArrayList<UserDetail>();
    private List<UserDetail> expectedSortBySalaryUserDetailList = new ArrayList<UserDetail>();
    private UserDetail userDetail1;
    private UserDetail userDetail2;
    private UserDetail userDetail3;

    @BeforeEach
    void setUp() {
        userDetail1 = new UserDetail();
        userDetail1.setId(1);
        userDetail1.setName("john");
        userDetail1.setSalary(4000.0f);

        userDetail2 = new UserDetail();
        userDetail2.setId(2);
        userDetail2.setName("john 2");
        userDetail2.setSalary(1000.0f);

        userDetail3 = new UserDetail();
        userDetail3.setId(3);
        userDetail3.setName("newJohn");
        userDetail3.setSalary(5000.0f);

        expectedNoSortUserDetailList.add(userDetail1);
        expectedNoSortUserDetailList.add(userDetail2);

        expectedSortByNameUserDetailList.add(userDetail1);
        expectedSortByNameUserDetailList.add(userDetail2);

        expectedSortBySalaryUserDetailList.add(userDetail2);
        expectedSortBySalaryUserDetailList.add(userDetail1);

    }

    @Test
    public void testGetUserDetailWithoutSorting() {

        when(userDetailRepository.getUserDetailWithNoSorting(1000.0f, 4000.0f, 0, 2)).thenReturn(expectedNoSortUserDetailList);
        List<UserDetail> userDetailList = userDetailService.getUserDetails("1000.0", "4000.0", "0", "2", "");
        assertThat(userDetailList)
                .isNotNull()
                .extracting("id", "name", "salary")
                .containsExactly(tuple(1, "john", 4000.0f),
                        tuple(2, "john 2", 1000.0f));
    }

    @Test
    public void testGetUserDetailWithSortingByName() {

        when(userDetailRepository.getUserDetailSortByName(1000.0f, 4000.0f, 0, 2)).thenReturn(expectedSortByNameUserDetailList);
        List<UserDetail> userDetailList = userDetailService.getUserDetails("1000.0", "4000.0", "0", "2", "name");
        assertThat(userDetailList)
                .isNotNull()
                .extracting("id", "name", "salary")
                .containsExactly(tuple(1, "john", 4000.0f),
                        tuple(2, "john 2", 1000.0f));
    }

    @Test
    public void testGetUserDetailWithSortingBySalary() {

        when(userDetailRepository.getUserDetailSortBySalary(1000.0f, 4000.0f, 0, 2)).thenReturn(expectedSortBySalaryUserDetailList);
        List<UserDetail> userDetailList = userDetailService.getUserDetails("1000.0", "4000.0", "0", "2", "salary");
        assertThat(userDetailList)
                .isNotNull()
                .extracting("id", "name", "salary")
                .containsExactly(tuple(2, "john 2", 1000.0f),
                        tuple(1, "john", 4000.0f));
    }

    @Test
    public void testUploadUserDetailWithExistingName() throws IOException {

        when(userDetailRepository.findUserDetailByNameIgnoreCase("newJohn")).thenReturn(userDetail3);
        when(userDetailRepository.findUserDetailByNameIgnoreCase("john")).thenReturn(userDetail1);
        boolean success = userDetailService.uploadUserDetail(new ClassPathResource("test.csv").getInputStream());
        assertThat(success).isTrue();
    }

    @Test
    public void testUploadUserDetailWithNewName() throws IOException {

        when(userDetailRepository.findUserDetailByNameIgnoreCase("newJohn")).thenReturn(null);
        when(userDetailRepository.findUserDetailByNameIgnoreCase("john")).thenReturn(null);
        boolean success = userDetailService.uploadUserDetail(new ClassPathResource("test.csv").getInputStream());
        assertThat(success).isTrue();
    }

    @Test
    public void testUploadUserDetailInvalidFileFormat() throws IOException {
        boolean success = userDetailService.uploadUserDetail(new ClassPathResource("test2.csv").getInputStream());
        assertThat(success).isFalse();
    }

    @Test
    public void testUploadUserDetailSalaryLessThanZero() throws IOException {
        boolean success = userDetailService.uploadUserDetail(new ClassPathResource("test3.csv").getInputStream());
        assertThat(success).isTrue();
    }

    @Test
    public void testUploadUserInvalidColumn() throws IOException {
        boolean success = userDetailService.uploadUserDetail(new ClassPathResource("test4.csv").getInputStream());
        assertThat(success).isFalse();
    }
}

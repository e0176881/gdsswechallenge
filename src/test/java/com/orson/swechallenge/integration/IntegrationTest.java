package com.orson.swechallenge.integration;

import com.orson.swechallenge.repository.UserDetailRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class IntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Test
    @Transactional
    void status200WhenUploadingValidCSV() throws Exception {

        // verify before state
        assertThat(userDetailRepository.findAll().size()).isEqualTo(2);
        assertThat(userDetailRepository.findUserDetailByNameIgnoreCase("john").getName()).isEqualToIgnoringCase("John");
        assertThat(userDetailRepository.findUserDetailByNameIgnoreCase("john").getSalary()).isEqualTo(2000f);

        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file",
                "test.csv",
                "text/csv",
                new ClassPathResource("test.csv").getInputStream());

        mvc.perform(MockMvcRequestBuilders.multipart("/upload")
                        .file(mockMultipartFile))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(1)));

        // verify after state
        assertThat(userDetailRepository.findAll().size()).isEqualTo(4);
        assertThat(userDetailRepository.findUserDetailByNameIgnoreCase("newJohn").getName()).isEqualToIgnoringCase("newjohn");
        assertThat(userDetailRepository.findUserDetailByNameIgnoreCase("newJohn").getSalary()).isEqualTo(8888.1f);
        assertThat(userDetailRepository.findUserDetailByNameIgnoreCase("john").getName()).isEqualToIgnoringCase("john");
        assertThat(userDetailRepository.findUserDetailByNameIgnoreCase("john").getSalary()).isEqualTo(1234.1f);
        assertThat(userDetailRepository.findUserDetailByNameIgnoreCase("john 2").getName()).isEqualToIgnoringCase("john 2");
        assertThat(userDetailRepository.findUserDetailByNameIgnoreCase("john 2").getSalary()).isEqualTo(0.0f);
        assertThat(userDetailRepository.findUserDetailByNameIgnoreCase("me").getName()).isEqualToIgnoringCase("me");
        assertThat(userDetailRepository.findUserDetailByNameIgnoreCase("me").getSalary()).isEqualTo(0.0f);
    }

    @Test
    @Transactional
    void skipInsertingRecordIfSalaryIsLessThanZero() throws Exception {

        // verify before state
        assertThat(userDetailRepository.findAll().size()).isEqualTo(2);

        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file",
                "test.csv",
                "text/csv",
                new ClassPathResource("test.csv").getInputStream());

        mvc.perform(MockMvcRequestBuilders.multipart("/upload")
                        .file(mockMultipartFile))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(1)));

        // verify after state
        assertThat(userDetailRepository.findAll().size()).isEqualTo(4);
        // verify name: hehe not inserted as salary < 0
        assertThat(userDetailRepository.findUserDetailByNameIgnoreCase("hehe")).isNull();

    }

    @Test
    @Transactional
    void skipUpdateRecordIfSalaryIsLessThanZero() throws Exception {

        // verify before state
        assertThat(userDetailRepository.findAll().size()).isEqualTo(2);
        assertThat(userDetailRepository.findUserDetailByNameIgnoreCase("john").getName()).isEqualToIgnoringCase("john");
        assertThat(userDetailRepository.findUserDetailByNameIgnoreCase("john").getSalary()).isEqualTo(2000.0f);

        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file",
                "test3.csv",
                "text/csv",
                new ClassPathResource("test3.csv").getInputStream());

        mvc.perform(MockMvcRequestBuilders.multipart("/upload")
                        .file(mockMultipartFile))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(1)));

        // verify after state
        assertThat(userDetailRepository.findAll().size()).isEqualTo(2);
        // verify name: john not updated as salary = <0
        assertThat(userDetailRepository.findUserDetailByNameIgnoreCase("john").getName()).isEqualToIgnoringCase("john");
        assertThat(userDetailRepository.findUserDetailByNameIgnoreCase("john").getSalary()).isEqualTo(2000.0f);

    }


    @Test
    void status500WhenUploadingInvalidCSV() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file",
                "test2.csv",
                "text/csv",
                new ClassPathResource("test2.csv").getInputStream());

        mvc.perform(MockMvcRequestBuilders.multipart("/upload")
                        .file(mockMultipartFile))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("Invalid format")))
                .andExpect(jsonPath("$.failure", is(0)));
    }


    @Test
    void status500WhenUploadingCSVWithInvalidCols() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file",
                "test4.csv",
                "text/csv",
                new ClassPathResource("test4.csv").getInputStream());

        mvc.perform(MockMvcRequestBuilders.multipart("/upload")
                        .file(mockMultipartFile))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("Invalid format")))
                .andExpect(jsonPath("$.failure", is(0)));
    }

    @Test
    void shouldReturnUserDetail() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results[0].name", is("John")))
                .andExpect(jsonPath("$.results[0].salary", is(2000.0)))
                .andExpect(jsonPath("$.results[1].name", is("John 2")))
                .andExpect(jsonPath("$.results[1].salary", is(4000.0)));
    }



}

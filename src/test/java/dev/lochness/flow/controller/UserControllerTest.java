package dev.lochness.flow.controller;

import dev.lochness.flow.domain.User;
import dev.lochness.flow.security.CustomUserPrincipal;
import dev.lochness.flow.service.UserService;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;
/*

    @Test
    @WithMockUser(
            username = "test",
            authorities = {"USER"}
    )
    void shouldReturnCorrectKarmaAmount() throws Exception {
        Authentication authentication = Mockito.mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(
                new org.springframework.security.core.userdetails.User(User)
                new CustomUserPrincipal(User.builder()
                        .username("test")
                        .karma(10)
                        .build()));
        given(userService.removeKarma(any(), eq(3))).willReturn(User.builder()
                .username("test")
                .karma(7)
                .build());
        mockMvc.perform(delete("/api/users/karma").param("count", "3"))
                .andExpect(status().isOk())
                .andExpect(content().string(new BaseMatcher<>() {
                    @Override
                    public boolean matches(Object currentKarma) {
                        Integer s = (Integer) currentKarma;
                        return currentKarma.equals(7);
                    }

                    @Override
                    public void describeTo(Description description) {
                    }
                }));
    }
*/

    @Test
    @WithAnonymousUser
    void shouldDenyAccessToKarmaOperations() throws Exception {
        this.mockMvc.perform(delete("/api/users/karma"))
                .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(
            username = "test",
            authorities = {"USER"}
    )
    void shouldDenyAccessIfWrongMethod() throws Exception {
        this.mockMvc.perform(get("/api/users/karma"))
                .andExpect(status().isMethodNotAllowed());
    }

}

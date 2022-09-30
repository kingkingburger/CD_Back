package SilkLoad.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class NotificationsTest {

    private Validator validator = null;

    @BeforeEach
    public void setupValidator() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    @DisplayName("알림 내용이 공백일 경우 실패한다.")
    public void test1() {


    }


}

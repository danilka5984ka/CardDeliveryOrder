package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static org.openqa.selenium.Keys.BACK_SPACE;

class DeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[data-test-id='city'] input").val(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "A", BACK_SPACE);
        $("[data-test-id='date'] input").val(firstMeetingDate);
        $("[data-test-id='name'] input").val(validUser.getName());
        $("[data-test-id='phone'] input").val(validUser.getPhone());
        $("[data-test-id='agreement'] .checkbox__box").click();
        $("[type='button'] .button__text").click();
        $("div .notification__content").shouldHave(Condition.exactText("Встреча успешно запланирована на " + firstMeetingDate));
        $("[data-test-id='date'] .input__control").sendKeys(Keys.CONTROL + "A",BACK_SPACE);
        $("[data-test-id='date'] .input__control").val(secondMeetingDate);
        $("[type='button'] .button__text").click();
        $x("//span[contains(text(), 'Перепланировать')]").click();
        $("[data-test-id='success-notification']").shouldHave(Condition.exactText("Успешно! Встреча успешно запланирована на " + secondMeetingDate),
                Duration.ofSeconds(15));
    }
}
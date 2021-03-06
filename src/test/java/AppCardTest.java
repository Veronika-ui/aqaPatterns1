import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

class AppCardTest {

    private DataGenerator.User user;

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        user = DataGenerator.getUserInfo();
    }


    @Test
    void shouldTestDeliveryCardFirst() {
        String date = DataGenerator.getDateMeeting(4);
        $("[data-test-id=city] input").setValue(user.getCity());
        $("[data-test-id=date] input")
                .sendKeys(Keys.chord(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE));
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue(user.getName());
        $("[data-test-id=phone] input").setValue(user.getPhone());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Успешно!")).shouldBe(visible);
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на " + date));
    }

    @Test
    void shouldRescheduleToAnotherDate() {
        String firstDate = DataGenerator.getDateMeeting(4);
        $("[data-test-id=city] input").setValue(user.getCity());
        $("[data-test-id=date] input")
                .sendKeys(Keys.chord(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE));
        $("[data-test-id=date] input").setValue(firstDate);
        $("[data-test-id=name] input").setValue(user.getName());
        $("[data-test-id=phone] input").setValue(user.getPhone());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Успешно!")).shouldBe(visible);
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на " + firstDate));
        String secondDate = DataGenerator.getDateMeeting(4);
        $("[data-test-id=date] input")
                .sendKeys(Keys.chord(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE));
        $("[data-test-id=date] input").setValue(secondDate);
        $(".button__text").click();
        $(withText("У вас уже запланирована встреча на другую дату. Перепланировать?")).shouldBe(visible);
        $("[data-test-id=replan-notification] button.button").click();
        $(byText("Успешно!")).shouldBe(visible);
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на " + secondDate));
    }

    @Test
    void shouldTestDeliveryCard() {
        String date = DataGenerator.getDateMeeting(4);
        $("[data-test-id=city] input").setValue(user.getCity());
        $("[data-test-id=date] input")
                .sendKeys(Keys.chord(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE));
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue(user.getName());
        $("[data-test-id=phone] input").setValue(user.getPhone());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Успешно!")).shouldBe(visible);
    }


    @Test
    void shouldTestDeliveryCardСheckingPopupWindow() {
        String date = DataGenerator.getDateMeeting(4);
        $("[data-test-id=city] input").setValue(user.getCity());
        $("[data-test-id=date] input");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue(user.getName());
        $("[data-test-id=phone] input").setValue(user.getPhone());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Успешно!")).shouldBe(visible);
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        String date1 = LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").setValue(date1);
        $$("button").find(exactText("Запланировать")).click();
        $(byText("Необходимо подтверждение")).shouldBe(visible);
        $$("button").find(exactText("Перепланировать")).click();
        $(byText("Успешно!")).shouldBe(visible);
    }

    @Test
    void shouldTestDeliveryCardWithoutDataAndAgreement() {
        $("[data-test-id=city] input").setValue("");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id=date] input").setValue("");
        $("[data-test-id=name] input").sendKeys(Keys.ESCAPE);
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue("");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id='city'] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldTestDeliveryCardWithoutName() {
        String date = DataGenerator.getDateMeeting(4);
        $("[data-test-id=city] input").setValue(user.getCity());
        $("[data-test-id=date] input");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Yaka34");
        $("[data-test-id=phone] input").setValue(user.getPhone());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id='name'] .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    void shouldTestDeliveryCardWithoutPhone() {
        String date = DataGenerator.getDateMeeting(4);
        $("[data-test-id=city] input").setValue(user.getCity());
        $("[data-test-id=date] input");
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue(user.getName());
        $("[data-test-id=phone] input").setValue("+79967544342");
        $(".button").click();
        $("[data-test-id='phone'] .input__sub").shouldHave(exactText("На указанный номер моб. тел. будет отправлен смс-код для подтверждения заявки на карту. Проверьте, что номер ваш и введен корректно."));
    }

    @Test
    void shouldTestDeliveryCardWithoutDate() {
        $("[data-test-id=city] input").setValue(DataGenerator.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id=date] input").setValue("1223565");
        $("[data-test-id=name] input").setValue(user.getName());
        $("[data-test-id=phone] input").setValue(user.getPhone());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id='date'] .input__sub").shouldHave(exactText("Неверно введена дата"));
    }

    @Test
    void shouldTestDeliveryCardIncorrectCity() {
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=city] input").setValue("Samara");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue(user.getName());
        $("[data-test-id=phone] input").setValue(user.getPhone());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id='city'] .input__sub").shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldTestDeliveryCardDateInPast() {
        String date = DataGenerator.getDateMeeting(2);
        $("[data-test-id=city] input").setValue(DataGenerator.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue(user.getName());
        $("[data-test-id=phone] input").setValue(user.getPhone());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id='date'] .input__sub").shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }


}
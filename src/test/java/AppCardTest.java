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

class CardDeliveryTest {

    private DataGenerator.User user;

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        user = DataGenerator.getUserInfo();
    }


    @Test
    void shouldTestDeliveryCardFirst() {
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=city] input").setValue(DataGenerator.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue(user.getName());
        $("[data-test-id=phone] input").setValue(user.getPhone());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Успешно!")).shouldBe(visible);
    }

    @Test
    void shouldTestDeliveryCardSameDate() {
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=city] input").setValue(DataGenerator.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue(user.getName());
        $("[data-test-id=phone] input").setValue(user.getPhone());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Успешно!")).shouldBe(visible);
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
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
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
        $("[data-test-id=date] input").setValue("");
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue("");
    }

    @Test
    void shouldTestDeliveryCardWithoutName() {
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=city] input").setValue(DataGenerator.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue(user.getPhone());
        $("[data-test-id=agreement]").click();
        $(".button").click();

    }

    @Test
    void shouldTestDeliveryCardWithoutPhone() {
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=city] input").setValue(DataGenerator.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue(user.getName());
        $("[data-test-id=phone] input").setValue("");
        $("[data-test-id=agreement]").click();
        $(".button").click();
    }

    @Test
    void shouldTestDeliveryCardWithoutDate() {
        $("[data-test-id=city] input").setValue(DataGenerator.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
        $("[data-test-id=date] input").setValue("");
        $("[data-test-id=name] input").setValue(user.getName());
        $("[data-test-id=phone] input").setValue(user.getPhone());
        $("[data-test-id=agreement]").click();
        $(".button").click();
    }

    @Test
    void shouldTestDeliveryCardIncorrectCity() {
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=city] input").setValue("Минск");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue(user.getName());
        $("[data-test-id=phone] input").setValue(user.getPhone());
        $("[data-test-id=agreement]").click();
        $(".button").click();
    }

    @Test
    void shouldTestDeliveryCardDateInPast() {
        String date = LocalDate.now().minusDays(2).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=city] input").setValue(DataGenerator.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue(user.getName());
        $("[data-test-id=phone] input").setValue(user.getPhone());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id=date] .input_invalid").shouldHave(exactText("Заказ на выбранную дату невозможен"));
    }


    @Test
    void shouldTestDeliveryCardWithoutAgreement() {
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=city] input").setValue(DataGenerator.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue(user.getName());
        $("[data-test-id=phone] input").setValue(user.getPhone());
    }


}
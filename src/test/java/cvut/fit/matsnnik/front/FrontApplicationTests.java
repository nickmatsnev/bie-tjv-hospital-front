package cvut.fit.matsnnik.front;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FrontApplicationTests {

    @Test
    void contextLoads() {
    }

}


<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Гибкий ввод даты</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-4">

    <form id="scheduleForm">
        <div class="mb-3">
            <label for="scheduleType" class="form-label">Выберите тип расписания</label>
            <select class="form-select" id="scheduleType" onchange="updateFormFields()">
                <option value="daily">Каждый день</option>
                <option value="nth_day">Каждый N-й день месяца</option>
                <option value="nth_week">Каждая N-я неделя месяца</option>
                <option value="nth_weekday">Каждая N-я суббота месяца</option>
            </select>
        </div>

        <div id="nthDayGroup" class="mb-3 d-none">
            <label for="nthDay" class="form-label">Выберите день месяца</label>
            <input type="number" id="nthDay" class="form-control" min="1" max="31">
        </div>

        <div id="nthWeekGroup" class="mb-3 d-none">
            <label for="nthWeek" class="form-label">Выберите неделю месяца</label>
            <select id="nthWeek" class="form-select">
                <option value="1">Первая</option>
                <option value="2">Вторая</option>
                <option value="3">Третья</option>
                <option value="4">Четвёртая</option>
            </select>
        </div>

        <div id="weekDayGroup" class="mb-3 d-none">
            <label for="weekDay" class="form-label">Выберите день недели</label>
            <select id="weekDay" class="form-select">
                <option value="1">Понедельник</option>
                <option value="2">Вторник</option>
                <option value="3">Среда</option>
                <option value="4">Четверг</option>
                <option value="5">Пятница</option>
                <option value="6">Суббота</option>
                <option value="7">Воскресенье</option>
            </select>
        </div>

        <button type="submit" class="btn btn-primary">Отправить</button>
    </form>

    <script>
        function updateFormFields() {
            let type = document.getElementById("scheduleType").value;
            
            document.getElementById("nthDayGroup").classList.toggle("d-none", type !== "nth_day");
            document.getElementById("nthWeekGroup").classList.toggle("d-none", type !== "nth_week" && type !== "nth_weekday");
            document.getElementById("weekDayGroup").classList.toggle("d-none", type !== "nth_weekday");
        }

        document.getElementById("scheduleForm").addEventListener("submit", function(event) {
            event.preventDefault();
            let type = document.getElementById("scheduleType").value;
            let output = "";

            if (type === "daily") {
                output = "Выбрано: каждый день";
            } else if (type === "nth_day") {
                let day = document.getElementById("nthDay").value;
                output = `Выбрано: каждый ${day}-й день месяца`;
            } else if (type === "nth_week") {
                let week = document.getElementById("nthWeek").value;
                output = `Выбрано: каждая ${week}-я неделя месяца`;
            } else if (type === "nth_weekday") {
                let week = document.getElementById("nthWeek").value;
                let day = document.getElementById("weekDay").selectedOptions[0].text;
                output = `Выбрано: каждая ${week}-я ${day} месяца`;
            }

            alert(output);
        });

        updateFormFields();
    </script>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>



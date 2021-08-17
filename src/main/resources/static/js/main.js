$(document).ready(function () {

    $("#add_task_btn").click(function (e) {
        let name = $('input[name="task_name"]').val();
        if(!name) {
            showError("Имя задачи не может быть пустым", "Ошибка при добавлении задачи");
            return;
        }
        let bounty = $('input[type="radio"][name="bounty"]:checked').val();
        let task = {
            taskName: name,
            bounty: bounty
        };
        $.ajax({
            type: "POST",
            url: "/api/tasks",
            contentType: "application/json",
            data: JSON.stringify(task),
            success: function (task) {
                $("#tasks tbody tr:last").before("<tr>" +
                    "<td>" + task.name + "</td>" +
                    "<td>" + task.bounty + "</td>" +
                    "<td></td>" +
                    "</tr>")
                $('input[type="text"]').val('');
                $('input:radio[name="bounty"]:first').attr('checked', true);

                if ($("#no_tasks_msg").length > 0) {
                    $("#no_tasks_msg").remove();
                    appendTaskControls();
                }
            },
            error: function (e) {
                alert("Unable to create task");
            }
        });
    });

    loadTasks();

    $("#chill_btn").click(function (e) {
        var currentKarma = $("#score").html();
        if (currentKarma > 0) {
            $("#popup").html("<h3>1 КАРМА = 10 МИН.</h3>" +
                "<input id='spend_karma_spinner' />" +
                "<input type='button' id='spend_karma_btn' value='Отдыхать'/>");
            applySpendKarmaSpinner(currentKarma);
            applySpendKarmaButton();
            $("#popup").dialog({
                title: "Отдых"
            });
        } else {
            showNotEnoughKarmaMessage();
        }
    });

    $("#money_btn").click(function (e) {
        var currentKarma = $("#score").html();
        if (currentKarma > 0) {
            $("#popup").html("<h3>1 КАРМА = 10 РУБ.</h3>" +
                "<input id='spend_karma_spinner' />" +
                "<input type='button' id='spend_karma_btn' value='Потратить'/>");
            applySpendKarmaSpinner(currentKarma);
            applySpendKarmaButton();
            $("#popup").dialog({
                title: "Покупки"
            });
        } else {
            showNotEnoughKarmaMessage();
        }
    });

    let now = new Date();
    $("#current_date").html(new Date().toLocaleDateString())
});

function loadTasks() {
    $.get("/api/tasks", function (tasks) {
        if (tasks) {
            $.each(tasks, function (i) {
                let task = tasks[i];
                $("#tasks tbody tr:last").before("<tr><td>" + task.name + "</td>" +
                    "<td>" + task.bounty + "</td>" +
                    "<td></td></tr>");
            });
            appendTaskControls();
        } else {
            appendEmptyListMessage("Отсутствуют задачи на текущий день")
        }
    });
}

function appendTaskControls() {
    $("#tasks tbody tr:first td:last")
        .append("<input type='button' id='complete_btn' value='Выполнено'/>" +
            "<input type='button' id='skip_btn' value='Пропустить'/>");

    $("#complete_btn").click(function (e) {
        $.get("/api/tasks/complete", function (score) {
            $("#tasks tbody tr:first").remove();
            if ($("#tasks tbody tr").length > 1) {
                appendTaskControls();
            } else {
                appendEmptyListMessage("Отличная работа! Вы выполнили все задачи на сегодня...");
            }
            updateKarma(score);
        });
    });

    $("#skip_btn").click(function (e) {
        $.get("/api/tasks/skip", function (score) {
            $("#tasks tbody tr:first").remove();
            if ($("#tasks tbody tr").length > 1) {
                appendTaskControls();
            } else {
                appendEmptyListMessage("В вашем списке больше не осталось задач...");
            }
            updateKarma(score);
        });
    });
}

function showNotEnoughKarmaMessage() {
    showError("У вас недостаточно кармы. Выполняйте текущие задачи," +
        " чтобы накопить карму.", "Не достаточно кармы");
}

function showError(message, title){
    $("#popup").html(message);
    $("#popup").dialog({
        title: title
    });
}

function appendEmptyListMessage(message) {
    $("#tasks tbody").prepend("<tr id='no_tasks_msg'><td colspan='3'>" + message + "</td></tr>");
}

function updateKarma(score) {
    $("#score").html(score);
}

function applySpendKarmaButton() {
    $("#spend_karma_btn").click(function (e) {
        $.ajax({
            type: "DELETE",
            url: "/api/users/karma",
            data: {
                count: $("#spend_karma_spinner").val()
            },
            success: function (karma) {
                $("#score").html(karma);
                $("#popup").dialog("close");
            },
            error: function (e) {
                $("#popup").dialog({
                    title: "Ошибка"
                });
                $("#popup").html("Не удалось потратить карму");
            }
        });
    });
}

function applySpendKarmaSpinner(currentKarma) {
    $("#spend_karma_spinner").spinner({
        min: 1,
        max: currentKarma,
        value: 1
    });
}
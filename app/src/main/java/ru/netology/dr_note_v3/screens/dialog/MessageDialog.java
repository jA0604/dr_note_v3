package ru.netology.dr_note_v3.screens.dialog;

import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import ru.netology.dr_note_v3.R;
import ru.netology.dr_note_v3.utils.Constants;

public class MessageDialog {

    public MessageDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Constants.APP_ACTIVITY);
        // добавляем различные компоненты в диалоговое окно
        builder.setTitle(title);
        builder.setMessage(message);
        // устанавливаем кнопку, которая отвечает за позитивный ответ
        builder.setPositiveButton(R.string.alert_dialog_str_btn_ok,
                // устанавливаем слушатель
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                    }
                });
        // объект Builder создал диалоговое окно и оно готово появиться на экране
        // вызываем этот метод, чтобы показать AlertDialog на экране пользователя
        builder.show();
    }
}

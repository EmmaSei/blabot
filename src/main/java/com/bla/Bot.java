package com.bla;

import com.bla.entities.Car;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.ForwardMessage;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bot extends TelegramLongPollingBot {
    private Car car = new Car();

    public static void main(String[] args) {
        ApiContextInitializer.init(); // Инициализируем апи
        TelegramBotsApi botapi = new TelegramBotsApi();
        try {
            botapi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод для приема сообщений.
     * @param update Содержит сообщение от пользователя.
     */
    // TODO: Replace blocks of 'If' to 'Switch-Case'
    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update.getCallbackQuery());
        System.out.println(update.getCallbackQuery().getData());
        System.out.println(update.getCallbackQuery().getFrom());
        System.out.println(update.getCallbackQuery().getId());
        System.out.println(update.getCallbackQuery().getMessage());
        System.out.println(update.getCallbackQuery().getMessage().getFrom());
        System.out.println(update.getCallbackQuery().getMessage().getText());
//        if(update.hasMessage()) {
//            Message msg = update.getMessage();
//        } else  if(update.hasCallbackQuery()) {
//            CallbackQuery callbackQuery = update.getCallbackQuery();
//            callbackQuery.getMessage()
//        }
//
//        Message msg = update.getMessage(); // Это нам понадобится
//        String txt = msg.getText();
//        if (txt.equals("/start")) {
//            sendMsg(msg, "Hello, world! This is your stupid bot!");
//            answerCallbackQuery(update.getCallbackQuery().getId(), "Blya!");
//        }
//        if (txt.equals("Предложить поездку")) {
//
//            sendMsg(msg, "Первая поездка! Расскажи о своей машине\nНомер: /number\nМарка: /marka\nМодель: /model\nЦвет: /color");
//            ForwardMessage forwardMessage = new ForwardMessage();
//            try {
//                Message msgF = forwardMessage(forwardMessage);
//                sendMsg(msgF, "qwer");
//            } catch (TelegramApiException e1) {
//                e1.printStackTrace();
//            }
//        }
//        if (txt.equals("Найти поездку")) {
//            sendMsg(msg, "Уже ищу!");
//
//        }
//        if (txt.equals("/number")) {
//            car.setNumber(update.toString());
//            sendMsg(msg, car.toString());
//
//        }
//        if (txt.equals("/marka")) {
//            sendMsg(msg, car.toString());
//
//        }
    }


    /**
     * Метод возвращает имя бота, указанное при регистрации.
     * @return имя бота
     */
    @Override
    public String getBotUsername() {
        return "blablarnd_Bot";
    }

    /**
     * Метод возвращает token бота для связи с сервером Telegram
     * @return token для бота
     */
    @Override
    public String getBotToken() {
        return "623664359:AAF616iuDKmdZWOcHL56P2sgbbwxY5LZsgg";
    }

    @SuppressWarnings("deprecation") // Означает то, что в новых версиях метод уберут или заменят
    private void sendMsg(Message msg, String text) {
        SendMessage s = new SendMessage();
        s.setChatId(msg.getChatId()); // Боту может писать не один человек, и поэтому чтобы отправить сообщение, грубо говоря нужно узнать куда его отправлять
        s.setText(text);
        setButtons(s);
        setInline();
        try { //Чтобы не крашнулась программа при вылете Exception
            sendMessage(s);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }
    public synchronized void setButtons(SendMessage sendMessage) {
        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add(new KeyboardButton("Предложить поездку"));

        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardSecondRow.add(new KeyboardButton("Найти поездку"));

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    private void setInline() {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> buttons1 = new ArrayList<>();
        buttons1.add(new InlineKeyboardButton().setText("Кнопка").setCallbackData("17"));
        buttons.add(buttons1);

        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();
        markupKeyboard.setKeyboard(buttons);
    }

    public synchronized void answerCallbackQuery(String callbackId, String message) {
        AnswerCallbackQuery answer = new AnswerCallbackQuery();
        answer.setCallbackQueryId(callbackId);
        answer.setText(message);
        answer.setShowAlert(true);
        try {
            answerCallbackQuery(answer);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
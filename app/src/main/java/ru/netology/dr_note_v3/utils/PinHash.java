package ru.netology.dr_note_v3.utils;

import java.security.MessageDigest;

public class PinHash {
    public String getHash(String pin) {
        return md5(pin);
    }

    private String md5(final String toEncrypt) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("md5");
            digest.update((toEncrypt+"ferrari").getBytes());
            final byte[] bytes = digest.digest();
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(String.format("%02X", bytes[i]));
            }
            return sb.toString().toLowerCase();
        } catch (Exception exc) {
            return ""; // Невозможно создать хэш!!!!
        }
    }
}

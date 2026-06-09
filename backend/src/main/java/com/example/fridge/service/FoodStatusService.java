package com.example.fridge.service;

import com.example.fridge.common.FoodStatus;
import com.example.fridge.vo.FoodItemVO;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FoodStatusService {

    private final int expiringSoonDays;

    public FoodStatusService(@Value("${fridge.expiring-soon-days:3}") int expiringSoonDays) {
        this.expiringSoonDays = expiringSoonDays;
    }

    public int getExpiringSoonDays() {
        return expiringSoonDays;
    }

    public LocalDate getThresholdDate() {
        return LocalDate.now().plusDays(expiringSoonDays);
    }

    public void fillStatus(List<FoodItemVO> items) {
        if (items == null) {
            return;
        }
        for (FoodItemVO item : items) {
            fillStatus(item);
        }
    }

    public void fillStatus(FoodItemVO item) {
        if (item == null || item.getExpireDate() == null) {
            return;
        }
        LocalDate today = LocalDate.now();
        long daysLeft = ChronoUnit.DAYS.between(today, item.getExpireDate());
        item.setDaysLeft(daysLeft);
        if (daysLeft < 0) {
            item.setStatus(FoodStatus.EXPIRED.getCode());
            item.setStatusText(FoodStatus.EXPIRED.getText());
        } else if (daysLeft <= expiringSoonDays) {
            item.setStatus(FoodStatus.EXPIRING_SOON.getCode());
            item.setStatusText(FoodStatus.EXPIRING_SOON.getText());
        } else {
            item.setStatus(FoodStatus.NORMAL.getCode());
            item.setStatusText(FoodStatus.NORMAL.getText());
        }
    }
}

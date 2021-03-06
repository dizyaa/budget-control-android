package dizel.budget_control.budget.domain.entity;

import androidx.annotation.Nullable;

import com.google.firebase.database.PropertyName;

//TODO переписать на котлин

public class CategoryApi {
    @PropertyName("categoryId")
    public String id = null;

    @PropertyName("categoryName")
    public String name = null;

    @PropertyName("categoryCurrency")
    public String currency = null;

    @PropertyName("categoryColor")
    public String color = null;

    private String money = null;

    @PropertyName("categoryMoney")
    public void setMoney(Object money) {
        if (money instanceof Double || money instanceof Long) {
            this.money = money.toString();
        } else {
            this.money = null;
        }
    }

    @Nullable
    @PropertyName("categoryMoney")
    public String getMoney() {
        return this.money;
    }
}

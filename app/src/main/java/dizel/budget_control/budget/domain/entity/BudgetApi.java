package dizel.budget_control.budget.domain.entity;

import androidx.annotation.Nullable;

import com.google.firebase.database.PropertyName;

//TODO переписать на котлин

public class BudgetApi {
    @PropertyName("title")
    public String title = null;

    @PropertyName("currency")
    public String currency = null;

    private String sum = null;

    @PropertyName("budgetSum")
    public void setSum(Object sum) {
        if (sum instanceof Double || sum instanceof Long) {
            this.sum = sum.toString();
        } else {
            this.sum = null;
        }
    }

    @Nullable
    @PropertyName("budgetSum")
    public String getSum() {
        return sum;
    }

}

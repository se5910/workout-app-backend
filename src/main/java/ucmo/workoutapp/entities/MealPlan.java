package ucmo.workoutapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.persistence.criteria.Fetch;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MealPlan extends Plan {

    @NotBlank(message = "Meal Plan Name required")
    private String name;

    // Each meal plan has many meals, and each meal belongs to one plan
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "mealPlan")
    private List<Meal> meals = new ArrayList<>();

    private Integer remainingCalories;

    private Integer remainingProteinGrams;

    private Integer remainingCarbsGrams;

    private Integer remainingFatsGrams;

    private Integer totalCalories;

    private Integer totalProteinCalories;

    private Integer totalCarbsCalories;

    private Integer totalFatsCalories;

    private Integer totalProteinGrams;

    private Integer totalCarbsGrams;

    private Integer totalFatsGrams;

    public MealPlan(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    public Integer getRemainingCalories() {
        return remainingCalories;
    }

    public void setRemainingCalories(Integer remainingCalories) {
        this.remainingCalories = remainingCalories;
    }

    public Integer getRemainingProteinGrams() {
        return remainingProteinGrams;
    }

    public void setRemainingProteinGrams(Integer remainingProteinGrams) {
        this.remainingProteinGrams = remainingProteinGrams;
    }

    public Integer getRemainingCarbsGrams() {
        return remainingCarbsGrams;
    }

    public void setRemainingCarbsGrams(Integer remainingCarbsGrams) {
        this.remainingCarbsGrams = remainingCarbsGrams;
    }

    public Integer getRemainingFatsGrams() {
        return remainingFatsGrams;
    }

    public void setRemainingFatsGrams(Integer remainingFatsGrams) {
        this.remainingFatsGrams = remainingFatsGrams;
    }

    public Integer getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(Integer totalCalories) {
        this.totalCalories = totalCalories;
    }

    public Integer getTotalProteinCalories() {
        return totalProteinCalories;
    }

    public void setTotalProteinCalories(Integer totalProteinCalories) {
        this.totalProteinCalories = totalProteinCalories;
    }

    public Integer getTotalCarbsCalories() {
        return totalCarbsCalories;
    }

    public void setTotalCarbsCalories(Integer totalCarbsCalories) {
        this.totalCarbsCalories = totalCarbsCalories;
    }

    public Integer getTotalFatsCalories() {
        return totalFatsCalories;
    }

    public void setTotalFatsCalories(Integer totalFatsCalories) {
        this.totalFatsCalories = totalFatsCalories;
    }

    public Integer getTotalProteinGrams() {
        return totalProteinGrams;
    }

    public void setTotalProteinGrams(Integer totalProteinGrams) {
        this.totalProteinGrams = totalProteinGrams;
    }

    public Integer getTotalCarbsGrams() {
        return totalCarbsGrams;
    }

    public void setTotalCarbsGrams(Integer totalCarbsGrams) {
        this.totalCarbsGrams = totalCarbsGrams;
    }

    public Integer getTotalFatsGrams() {
        return totalFatsGrams;
    }

    public void setTotalFatsGrams(Integer totalFatsGrams) {
        this.totalFatsGrams = totalFatsGrams;
    }
}

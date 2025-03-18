/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author AD
 */
public class VipTerm {
    private int vipTermId;
    private String vipType;
    private String termName;
    private int duration;
    private double loanRate;
    private double savingsRate;
    private double price;

    public VipTerm(int vipTermId, String vipType, String termName, int duration, double loanRate, double savingsRate, double price) {
        this.vipTermId = vipTermId;
        this.vipType = vipType;
        this.termName = termName;
        this.duration = duration;
        this.loanRate = loanRate;
        this.savingsRate = savingsRate;
        this.price = price;
    }

    public int getVipTermId() {
        return vipTermId;
    }

    public String getVipType() {
        return vipType;
    }

    public String getTermName() {
        return termName;
    }

    public int getDuration() {
        return duration;
    }

    public double getLoanRate() {
        return loanRate;
    }

    public double getSavingsRate() {
        return savingsRate;
    }

    public double getPrice() {
        return price;
    }

    public void setVipTermId(int vipTermId) {
        this.vipTermId = vipTermId;
    }

    public void setVipType(String vipType) {
        this.vipType = vipType;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setLoanRate(double loanRate) {
        this.loanRate = loanRate;
    }

    public void setSavingsRate(double savingsRate) {
        this.savingsRate = savingsRate;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    

    @Override
    public String toString() {
        return "VipTerm{" + "vipTermId=" + vipTermId + ", vipType=" + vipType + ", termName=" + termName + ", duration=" + duration + ", loanRate=" + loanRate + ", savingsRate=" + savingsRate + ", price=" + price + '}';
    }
    
}

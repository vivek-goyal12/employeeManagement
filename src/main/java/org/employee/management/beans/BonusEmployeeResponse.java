package org.employee.management.beans;

import java.util.List;

public class BonusEmployeeResponse {
    private String errorMessage;
    private List<CurrencyEmployees> data;

    // Constructors, Getters and Setters

    public BonusEmployeeResponse(String errorMessage, List<CurrencyEmployees> data) {
        this.errorMessage = errorMessage;
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<CurrencyEmployees> getData() {
        return data;
    }

    public void setData(List<CurrencyEmployees> data) {
        this.data = data;
    }

    public static class CurrencyEmployees {
        private String currency;
        private List<EmployeeInfo> employees;

        public CurrencyEmployees(String currency, List<EmployeeInfo> employees) {
            this.currency = currency;
            this.employees = employees;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public List<EmployeeInfo> getEmployees() {
            return employees;
        }

        public void setEmployees(List<EmployeeInfo> employees) {
            this.employees = employees;
        }

        public static class EmployeeInfo {
            private String empName;
            private Double amount;

            public EmployeeInfo(String empName, Double amount) {
                this.empName = empName;
                this.amount = amount;
            }

            public String getEmpName() {
                return empName;
            }

            public void setEmpName(String empName) {
                this.empName = empName;
            }

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }
        }
    }
}

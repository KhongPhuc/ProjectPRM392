package com.example.projectprm392.java.Response;

import com.example.projectprm392.java.Entities.OrderDetail;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderResponse {
        private boolean success;
        private String message;
        @SerializedName("OrderID")
        private int orderID;
        private List<OrderDetail> orderDetails;
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String statuss) {
            status = statuss;
        }

    public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }

        public int getOrderID() {
            return orderID;
        }

        public List<OrderDetail> getOrderDetails() {
            return orderDetails;
        }
    }



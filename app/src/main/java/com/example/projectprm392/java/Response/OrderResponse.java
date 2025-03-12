package com.example.projectprm392.java.Response;

import com.example.projectprm392.java.Entities.OrderDetail;

import java.util.List;

public class OrderResponse {
        private boolean success;
        private String message;
        private int orderID;
        private List<OrderDetail> orderDetails;

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



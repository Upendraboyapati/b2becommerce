package com.b2b.service;

import java.math.BigDecimal;
import java.util.Map;

public interface AnalyticsService {
    BigDecimal getTotalRevenue();
    BigDecimal getTotalProfit();
    int getTotalOrders();
    int getTotalUsers();
    Map<String, Object> getDashboardStats();
}

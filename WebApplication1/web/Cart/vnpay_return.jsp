<%@ page import="DAO.OrderDAO" %>
<%
    String responseCode = request.getParameter("vnp_ResponseCode");
    int orderId = Integer.parseInt(request.getParameter("orderId"));

    if ("00".equals(responseCode)) {
        new OrderDAO().updateOrderStatus(orderId, 2); // 2 = ?� thanh to�n
        out.println("Thanh to�n th�nh c�ng donn h�ng #" + orderId);
    } else {
        out.println("Thanh to�n th?t b?i. M� l?i: " + responseCode);
    }
%>

<%@ page import="DAO.OrderDAO" %>
<%
    String responseCode = request.getParameter("vnp_ResponseCode");
    int orderId = Integer.parseInt(request.getParameter("orderId"));

    if ("00".equals(responseCode)) {
        new OrderDAO().updateOrderStatus(orderId, 2); // 2 = ?ã thanh toán
        out.println("Thanh toán thành công donn hàng #" + orderId);
    } else {
        out.println("Thanh toán th?t b?i. Mã l?i: " + responseCode);
    }
%>

package TrackingManager;
import java.util.function.Consumer;

class Order {
    String orderID;    // orderID -> orderID
    String destinationAddress;
    String sendingAddress;
    boolean delivered;   // Available status
    Order left, right;

    public Order(String orderID, String destinationAddress, String sendingAddress) {
        this.orderID = orderID;
        this.destinationAddress = destinationAddress;
        this.sendingAddress = sendingAddress;
        this.delivered = false;
    }

    @Override
    public String toString() {
        return "Order ID: " + orderID + ", Destination Address: " + destinationAddress
                + ", SendingAddress: " + sendingAddress + ", : " + (delivered ? "delivered" : "delivering");
    }
}

class TrackingSystem {

    private Order root;

    public void addOrder(String orderID, String destinationAddress, String sendingAddress) {
        root = addOrder(root, orderID, destinationAddress, sendingAddress);
    }

    private Order addOrder(Order node, String orderID, String destinationAddress, String sendingAddress) {
        if (node == null) {
            return new Order(orderID, destinationAddress, sendingAddress);
        }
        if (orderID.compareTo(node.orderID) < 0) {
            node.left = addOrder(node.left, orderID, destinationAddress, sendingAddress);
        } else if (orderID.compareTo(node.orderID) > 0) {
            node.right = addOrder(node.right, orderID, destinationAddress, sendingAddress);
        }
        return node;
    }

    public Order searchOrder(String orderID) {
        return searchOrder(root, orderID);
    }

    private Order searchOrder(Order node, String orderID) {
        if (node == null || node.orderID.equals(orderID)) {
            return node;
        }
        if (orderID.compareTo(node.orderID) < 0) {
            return searchOrder(node.left, orderID);
        }
        return searchOrder(node.right, orderID);
    }

    public void deleteOrder(String orderID) {
        root = deleteOrder(root, orderID);
    }



    private Order deleteOrder(Order node, String orderID) {
        if (node == null) {
            return null;
        }
        if (orderID.compareTo(node.orderID) < 0) {
            node.left = deleteOrder(node.left, orderID);
        } else if (orderID.compareTo(node.orderID) > 0) {
            node.right = deleteOrder(node.right, orderID);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }
            Order minNode = findMin(node.right);
            node.orderID = minNode.orderID;
            node.destinationAddress = minNode.destinationAddress;
            node.sendingAddress = minNode.sendingAddress;
            node.right = deleteOrder(node.right, minNode.orderID);
        }
        return node;
    }

    
    private Order findMin(Order node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public void displayOrdersInOrder(Consumer<Order> action) {
        inorderTraversal(root, action);
    }

    private void inorderTraversal(Order node, Consumer<Order> action) {
        if (node != null) {
            inorderTraversal(node.left, action);
            action.accept(node);
            inorderTraversal(node.right, action);
        }
    }

    public int countOrders() {
        return countNodes(root);
    }

    private int countNodes(Order node) {
        if (node == null) {
            return 0;
        }
        return 1 + countNodes(node.left) + countNodes(node.right);
    }

    public boolean borrowOrder(String orderID) {
        Order Order = searchOrder(orderID);
        if (Order == null) {
            return false;
        }
        if (Order.delivered) {
            return false;
        }
        Order.delivered = true;
        return true;
    }

    public boolean returnOrder(String orderID) {
        Order Order = searchOrder(orderID);
        if (Order == null) {
            return false;
        }
        if (!Order.delivered) {
            return false;
        }
        Order.delivered = false;
        return true;
    }


}


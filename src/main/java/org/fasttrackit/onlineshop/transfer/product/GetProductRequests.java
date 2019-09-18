package org.fasttrackit.onlineshop.transfer.product;

public class GetProductRequests {
    private String partialName;
    //wrapper classes accept null values as well
    private Integer minimumQuantity;

    public String getPartialName() {
        return partialName;
    }

    public void setPartialName(String partialName) {
        this.partialName = partialName;
    }

    public Integer getMinimumQuantity() {
        return minimumQuantity;
    }

    public void setMinimumQuantity(Integer minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
    }

    @Override
    public String toString() {
        return "GetProductRequests{" +
                "partialName='" + partialName + '\'' +
                ", minimumQuantity=" + minimumQuantity +
                '}';
    }
}
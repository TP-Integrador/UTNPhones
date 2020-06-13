package edu.utn.utnphones.projections;

import java.util.Date;

public interface GetCalls {
    String getOriginNumber();
    String getOriginCity();
    String getDestinationNumber();
    String getDestinationCity();
    float getTotalPrice();
    int getDuration();
    Date getCallDate();
}

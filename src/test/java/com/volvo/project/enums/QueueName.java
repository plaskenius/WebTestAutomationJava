/*
 * author PawelPie: Pawel
 * ID: A049473
 */
package com.project.project.enums;

public enum QueueName {

    DealerCTDIQueue("system.TDI.DLR.IN"), OrganizationDistribution("system.ORGANIZATION.DISTRIBUTION"),
    OrganizationDistributionT3("system.ORGANIZATION.DISTRIBUTION_T3");

    private final String value;

    private QueueName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}

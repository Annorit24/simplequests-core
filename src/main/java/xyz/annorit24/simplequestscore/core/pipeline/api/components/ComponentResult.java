package xyz.annorit24.simplequestscore.core.pipeline.api.components;

/**
 * @author Annorit24
 * Created on 22/02/2020
 */
public enum  ComponentResult {

    SUCCESS(false,true),
    FAILURE(false,false),
    CRITICAL_FAILURE(true,true);

    private boolean critical;
    private boolean success;

    ComponentResult(boolean critical, boolean success) {
        this.critical = critical;
        this.success = success;
    }

    public boolean isCritical() {
        return critical;
    }

    public boolean isSuccess() {
        return success;
    }

}

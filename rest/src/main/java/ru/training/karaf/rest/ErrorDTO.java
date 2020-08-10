package ru.training.karaf.rest;

class ErrorDTO {
    private String error;

    public ErrorDTO(String error) {
        this.error = error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}

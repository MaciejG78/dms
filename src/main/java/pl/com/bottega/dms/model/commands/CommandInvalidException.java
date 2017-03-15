package pl.com.bottega.dms.model.commands;

import pl.com.bottega.dms.model.commands.Validatable;

/**
 * Created by maciek on 12.03.2017.
 */
public class CommandInvalidException extends RuntimeException {

    private Validatable.ValidationErrors errors;

    public CommandInvalidException(Validatable.ValidationErrors errors){
        this.errors = errors;
    }

    public Validatable.ValidationErrors getErrors(){
        return errors;
    }
}

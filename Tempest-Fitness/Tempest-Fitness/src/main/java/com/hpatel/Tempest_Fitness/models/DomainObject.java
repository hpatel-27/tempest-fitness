package com.hpatel.Tempest_Fitness.models;

import java.io.Serializable;

/**
 * The root class for all of our persistent entities. Defines no fields or
 * methods, but is used to provide a common superclass that the `Service`
 * methods can use.
 *
 * @author Harsh Patel
 *
 */
abstract public class DomainObject {

    /**
     * Returns the ID of this object. the ID is used for uniquely identifying
     * this object for persistent storage in the database.
     *
     * @return ID of the object
     */
    public abstract Serializable getId ();
}

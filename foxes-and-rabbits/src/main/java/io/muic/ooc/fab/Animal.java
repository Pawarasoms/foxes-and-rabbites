package io.muic.ooc.fab;

import java.util.List;
import java.util.Random;

public abstract class Animal {

    private int age=0;

    protected abstract int getMaxAge();

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    protected abstract int getBreedingAge();

    // Whether the rabbit is alive or not.
    private boolean alive=true;

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    // The fox's position.
    private Location location;
    // The field occupied.
    private Field field;


    public Location getLocation() {
        return location;
    }

    public Field getField() {
        return field;
    }

    // A shared random number generator to control breeding.
    private static final Random RANDOM = new Random();

    public Animal(boolean randomAge, Field field, Location location) {
        this.location = location;
        this.field = field;
        setLocation(location);
        if (randomAge) {
            setAge(RANDOM.nextInt(getMaxAge()));
        }
    }

    /**
     * Increase the age. This could result in the rabbit's death.
     */
    protected void incrementAge() {
        age++;
        if (age > getMaxAge()) {
            setDead();
        }
    }


    /**
     * A rabbit can breed if it has reached the breeding age.
     *
     * @return true if the rabbit can breed, false otherwise.
     */
    protected boolean canBreed() {
        return getAge() >= getBreedingAge();
    }

    /**
     * Check whether the fox is alive or not.
     *
     * @return True if the fox is still alive.
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Place the rabbit at the new location in the given field.
     *
     * @param newLocation The rabbit's new location.
     */
    protected void setLocation(Location newLocation) {
        if (location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    /**
     * Indicate that the rabbit is no longer alive. It is removed from the
     * field.
     */

    protected void setDead() {
        setAlive(false);
        if (location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Generate a number representing the number of births, if it can breed.
     *
     * @return The number of births (may be zero).
     */
    protected int breed() {
        int births = 0;
        if (canBreed() && RANDOM.nextDouble() <= getBreedingProbability()) {
            births = RANDOM.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }

    protected abstract int getMaxLitterSize();

    protected abstract double getBreedingProbability();

    public abstract void act(List<Animal> newAnimals);


}

public class Pet {
    private String name;
    private int age;
    private String gender;
    private String breed;
    private boolean isAdopted;
    private String adopterName;
    private String adopterContact;

    public Pet(String name, int age, String gender, String breed, boolean isAdopted, String adopterName, String adopterContact) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.breed = breed;
        this.isAdopted = isAdopted;
        this.adopterName = adopterName;
        this.adopterContact = adopterContact;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public boolean isAdopted() {
        return isAdopted;
    }

    public void setAdopted(boolean adopted) {
        isAdopted = adopted;
    }

    public String getAdopterName() {
        return adopterName;
    }

    public void setAdopterName(String adopterName) {
        this.adopterName = adopterName;
    }

    public String getAdopterContact() {
        return adopterContact;
    }

    public void setAdopterContact(String adopterContact) {
        this.adopterContact = adopterContact;
    }

    @Override
    public String toString() {
        return "Name: " + name +
                ", Age: " + age +
                ", Gender: " + gender +
                ", Breed: " + breed +
                (isAdopted ? ", Adopted, Adopter: " + adopterName + ", Contact: " + adopterContact : ", Not Adopted");
    }
}

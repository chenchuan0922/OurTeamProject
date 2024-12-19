import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Scanner;

public class PetManager {
    private List<Pet> pets;
    private String dataFile;

    // Default constructor uses "pets.csv"
    public PetManager() {
        this("pets.csv");
    }

    // Overloaded constructor for custom data file paths (useful for testing)
    public PetManager(String dataFilePath) {
        pets = new ArrayList<>();
        this.dataFile = dataFilePath;
        loadData();
    }

    // Adds a new pet to the list. Returns false if the pet name already exists.
    public boolean addPet(Pet pet) {
        if (isPetNameExists(pet.getName())) {
            return false;
        }
        pets.add(pet);
        System.out.println(Utilities.GREEN + "Pet added." + Utilities.RESET);
        return true;
    }

    // Checks if a pet name exists (case-insensitive)
    public boolean isPetNameExists(String name) {
        for (Pet pet : pets) {
            if (pet.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    // Displays all pets with formatting
    public void showAllPets() {
        if (pets.isEmpty()) {
            System.out.println(Utilities.YELLOW + "No pets available." + Utilities.RESET);
            return;
        }
        System.out.println(Utilities.CYAN + "----------------------------------------" + Utilities.RESET);
        for (Pet pet : pets) {
            System.out.println(pet);
            System.out.println(Utilities.CYAN + "----------------------------------------" + Utilities.RESET);
        }
    }

    // Deletes a pet by name
    public void deletePetByName(String name) {
        boolean removed = pets.removeIf(pet -> pet.getName().equalsIgnoreCase(name));
        if (removed) {
            System.out.println(Utilities.GREEN + "Pet deleted." + Utilities.RESET);
        } else {
            System.out.println(Utilities.RED + "Pet named " + name + " not found." + Utilities.RESET);
        }
    }

    // Updates pet information by name
    public void updatePetInfo(String name, int age, String gender, String breed) {
        for (Pet pet : pets) {
            if (pet.getName().equalsIgnoreCase(name)) {
                pet.setAge(age);
                pet.setGender(gender);
                pet.setBreed(breed);
                System.out.println(Utilities.GREEN + "Pet information updated!" + Utilities.RESET);
                return;
            }
        }
        System.out.println(Utilities.RED + "Pet named " + name + " not found." + Utilities.RESET);
    }

    // Searches for a pet by exact name
    public void searchPetByName(String name) {
        boolean found = false;
        for (Pet pet : pets) {
            if (pet.getName().equalsIgnoreCase(name)) {
                System.out.println(Utilities.GREEN + "Found pet: " + pet + Utilities.RESET);
                found = true;
            }
        }
        if (!found) {
            System.out.println(Utilities.RED + "Pet named " + name + " not found." + Utilities.RESET);
        }
    }

    // Searches for pets by partial name match
    public void searchPetsByPartialName(String partialName) {
        List<Pet> matchedPets = new ArrayList<>();
        String lowerPartial = partialName.toLowerCase();
        for (Pet pet : pets) {
            if (pet.getName().toLowerCase().contains(lowerPartial)) {
                matchedPets.add(pet);
            }
        }
        if (matchedPets.isEmpty()) {
            System.out.println(Utilities.RED + "No pets found containing \"" + partialName + "\"." + Utilities.RESET);
        } else {
            System.out.println(Utilities.GREEN + "Found the following pets:" + Utilities.RESET);
            System.out.println(Utilities.CYAN + "----------------------------------------" + Utilities.RESET);
            for (Pet pet : matchedPets) {
                System.out.println(pet);
                System.out.println(Utilities.CYAN + "----------------------------------------" + Utilities.RESET);
            }
        }
    }

    // Retrieves all unique breeds
    public Set<String> getAllBreeds() {
        Set<String> breeds = new HashSet<>();
        for (Pet pet : pets) {
            breeds.add(pet.getBreed());
        }
        return breeds;
    }

    // Filters pets by breed
    public void filterPetsByBreed(String breed) {
        List<Pet> filteredPets = new ArrayList<>();
        String lowerBreed = breed.toLowerCase();
        for (Pet pet : pets) {
            if (pet.getBreed().toLowerCase().equals(lowerBreed)) {
                filteredPets.add(pet);
            }
        }
        if (filteredPets.isEmpty()) {
            System.out.println(Utilities.RED + "No pets found with breed \"" + breed + "\"." + Utilities.RESET);
        } else {
            System.out.println(Utilities.GREEN + "Pets with breed \"" + breed + "\":" + Utilities.RESET);
            System.out.println(Utilities.CYAN + "----------------------------------------" + Utilities.RESET);
            for (Pet pet : filteredPets) {
                System.out.println(pet);
                System.out.println(Utilities.CYAN + "----------------------------------------" + Utilities.RESET);
            }
        }
    }

    // Filters pets by adoption status
    public void filterPetsByAdoptionStatus(boolean isAdopted) {
        List<Pet> filteredPets = new ArrayList<>();
        for (Pet pet : pets) {
            if (pet.isAdopted() == isAdopted) {
                filteredPets.add(pet);
            }
        }
        if (filteredPets.isEmpty()) {
            System.out.println(Utilities.YELLOW + (isAdopted ? "No adopted pets." : "No available pets for adoption.") + Utilities.RESET);
        } else {
            System.out.println(Utilities.GREEN + (isAdopted ? "Adopted pets:" : "Available pets for adoption:") + Utilities.RESET);
            System.out.println(Utilities.CYAN + "----------------------------------------" + Utilities.RESET);
            for (Pet pet : filteredPets) {
                System.out.println(pet);
                System.out.println(Utilities.CYAN + "----------------------------------------" + Utilities.RESET);
            }
        }
    }

    // Handles the pet adoption process
    public void adoptPet(String name, Scanner scanner) {
        for (Pet pet : pets) {
            if (pet.getName().equalsIgnoreCase(name)) {
                if (pet.isAdopted()) {
                    System.out.println(Utilities.RED + "This pet has already been adopted!" + Utilities.RESET);
                } else {
                    System.out.print("Enter adopter's name: ");
                    String adopterName = scanner.nextLine().trim();
                    while (adopterName.isEmpty()) {
                        System.out.print("Adopter's name cannot be empty. Please re-enter: ");
                        adopterName = scanner.nextLine().trim();
                    }

                    System.out.print("Enter adopter's contact: ");
                    String adopterContact = scanner.nextLine().trim();
                    while (adopterContact.isEmpty()) {
                        System.out.print("Adopter's contact cannot be empty. Please re-enter: ");
                        adopterContact = scanner.nextLine().trim();
                    }

                    pet.setAdopted(true);
                    pet.setAdopterName(adopterName);
                    pet.setAdopterContact(adopterContact);
                    System.out.println(Utilities.GREEN + "Congratulations! You have successfully adopted " + pet.getName() + "." + Utilities.RESET);
                }
                return;
            }
        }
        System.out.println(Utilities.RED + "Pet named " + name + " not found." + Utilities.RESET);
    }

    // Saves pet data to CSV file
    public void saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile))) {
            for (Pet pet : pets) {
                String line = pet.getName() + "," + pet.getAge() + "," + pet.getGender() + "," +
                        pet.getBreed() + "," + pet.isAdopted() + "," +
                        pet.getAdopterName() + "," + pet.getAdopterContact();
                writer.write(line);
                writer.newLine();
            }
            System.out.println(Utilities.GREEN + "Data saved to " + dataFile + "." + Utilities.RESET);
        } catch (IOException e) {
            System.out.println(Utilities.RED + "Error saving data: " + e.getMessage() + Utilities.RESET);
        }
    }

    // Loads pet data from CSV file
    public void loadData() {
        File file = new File(dataFile);
        if (!file.exists()) {
            System.out.println(Utilities.YELLOW + "Data file not found. Starting new pet management." + Utilities.RESET);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            pets.clear(); // Clear current list to avoid duplicates
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", -1); // -1 to include empty fields
                if (parts.length < 7) {
                    System.out.println(Utilities.RED + "Skipping invalid data line: " + line + Utilities.RESET);
                    continue;
                }
                String name = parts[0];
                int age = Integer.parseInt(parts[1]);
                String gender = parts[2];
                String breed = parts[3];
                boolean isAdopted = Boolean.parseBoolean(parts[4]);
                String adopterName = parts[5];
                String adopterContact = parts[6];
                Pet pet = new Pet(name, age, gender, breed, isAdopted, adopterName, adopterContact);
                pets.add(pet);
            }
            System.out.println(Utilities.GREEN + "Data loaded from " + dataFile + "." + Utilities.RESET);
        } catch (IOException | NumberFormatException e) {
            System.out.println(Utilities.RED + "Error loading data: " + e.getMessage() + Utilities.RESET);
        }
    }
}

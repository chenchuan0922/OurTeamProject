import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        PetManager petManager = new PetManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            int choice = getUserChoice(scanner);

            switch (choice) {
                case 1:
                    addPetFlow(petManager, scanner);
                    break;
                case 2:
                    petManager.showAllPets();
                    break;
                case 3:
                    deletePetFlow(petManager, scanner);
                    break;
                case 4:
                    updatePetFlow(petManager, scanner);
                    break;
                case 5:
                    searchPetFlow(petManager, scanner);
                    break;
                case 6:
                    adoptPetFlow(petManager, scanner);
                    break;
                case 7:
                    partialSearchFlow(petManager, scanner);
                    break;
                case 8:
                    filterPetsFlow(petManager, scanner);
                    break;
                case 9:
                    System.out.println(Utilities.PURPLE + "Exiting the program." + Utilities.RESET);
                    petManager.saveData(); // Save data
                    scanner.close();
                    return;
                default:
                    System.out.println(Utilities.RED + "Invalid option, please try again." + Utilities.RESET);
            }

            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }

    // Prints the main menu with blue color
    private static void printMenu() {
        System.out.println(Utilities.BLUE + "========================================" + Utilities.RESET);
        System.out.println(Utilities.BLUE + "    Welcome to Pet Management System" + Utilities.RESET);
        System.out.println(Utilities.BLUE + "========================================" + Utilities.RESET);
        System.out.println(Utilities.BLUE + "Please choose an option:" + Utilities.RESET);
        System.out.println("1. Add Pet");
        System.out.println("2. Show All Pets");
        System.out.println("3. Delete Pet");
        System.out.println("4. Update Pet Information");
        System.out.println("5. Search Pet");
        System.out.println("6. Adopt Pet");
        System.out.println("7. Partial Match Search");
        System.out.println("8. Filter Pets");
        System.out.println("9. Exit");
        System.out.print(Utilities.BLUE + "Enter your choice (1-9): " + Utilities.RESET);
    }

    // Gets and validates user menu choice
    private static int getUserChoice(Scanner scanner) {
        int choice = -1;
        while (choice < 1 || choice > 9) {
            try {
                String input = scanner.nextLine().trim();
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > 9) {
                    System.out.print(Utilities.RED + "Invalid option, please enter a number between 1 and 9: " + Utilities.RESET);
                }
            } catch (NumberFormatException e) {
                System.out.print(Utilities.RED + "Invalid input, please enter a number: " + Utilities.RESET);
            }
        }
        return choice;
    }

    // Handles the process of adding a pet
    private static void addPetFlow(PetManager petManager, Scanner scanner) {
        String name = "";
        while (true) {
            System.out.print("Enter pet's name: ");
            name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println(Utilities.RED + "Pet name cannot be empty. Please re-enter." + Utilities.RESET);
                continue;
            }
            if (petManager.isPetNameExists(name)) {
                System.out.println(Utilities.RED + "Pet name already exists. Please enter a unique name." + Utilities.RESET);
            } else {
                break;
            }
        }

        int age = -1;
        while (age <= 0) {
            System.out.print("Enter pet's age (positive integer): ");
            String ageInput = scanner.nextLine().trim();
            try {
                age = Integer.parseInt(ageInput);
                if (age <= 0) {
                    System.out.println(Utilities.RED + "Age must be greater than zero. Please re-enter!" + Utilities.RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(Utilities.RED + "Invalid input, please enter an integer!" + Utilities.RESET);
            }
        }

        String gender = "";
        while (true) {
            System.out.print("Enter pet's gender (m/f): ");
            String genderInput = scanner.nextLine().trim();
            try {
                gender = Utilities.convertMaleFemaleToGender(genderInput);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(Utilities.RED + e.getMessage() + Utilities.RESET);
            }
        }

        String breed = "";
        while (breed.isEmpty()) {
            System.out.print("Enter pet's breed: ");
            breed = scanner.nextLine().trim();
            if (breed.isEmpty()) {
                System.out.println(Utilities.RED + "Breed cannot be empty. Please re-enter." + Utilities.RESET);
            }
        }

        boolean isAdopted = false;
        while (true) {
            System.out.print("Is the pet adopted? (y/n): ");
            String adoptedInput = scanner.nextLine().trim().toLowerCase();
            try {
                isAdopted = Utilities.convertYesNoToBoolean(adoptedInput);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(Utilities.RED + e.getMessage() + Utilities.RESET);
            }
        }

        String adopterName = "";
        String adopterContact = "";
        if (isAdopted) {
            while (true) {
                System.out.print("Enter adopter's name: ");
                adopterName = scanner.nextLine().trim();
                if (adopterName.isEmpty()) {
                    System.out.println(Utilities.RED + "Adopter's name cannot be empty. Please re-enter." + Utilities.RESET);
                } else {
                    break;
                }
            }

            while (true) {
                System.out.print("Enter adopter's contact: ");
                adopterContact = scanner.nextLine().trim();
                if (adopterContact.isEmpty()) {
                    System.out.println(Utilities.RED + "Adopter's contact cannot be empty. Please re-enter." + Utilities.RESET);
                } else {
                    break;
                }
            }
        }

        Pet pet = new Pet(name, age, gender, breed, isAdopted, adopterName, adopterContact);
        boolean added = petManager.addPet(pet);
        if (!added) {
            System.out.println(Utilities.RED + "Failed to add pet, name already exists." + Utilities.RESET);
        }
    }

    // Handles the process of deleting a pet
    private static void deletePetFlow(PetManager petManager, Scanner scanner) {
        System.out.println("\n" + Utilities.PURPLE + "Current Pets:" + Utilities.RESET);
        petManager.showAllPets();

        System.out.print("Enter the name of the pet to delete: ");
        String deleteName = scanner.nextLine().trim();
        if (deleteName.isEmpty()) {
            System.out.println(Utilities.RED + "Pet name cannot be empty." + Utilities.RESET);
            return;
        }
        petManager.deletePetByName(deleteName);
    }

    // Handles the process of updating pet information
    private static void updatePetFlow(PetManager petManager, Scanner scanner) {
        System.out.println("\n" + Utilities.PURPLE + "Current Pets:" + Utilities.RESET);
        petManager.showAllPets();

        System.out.print("Enter the name of the pet to update: ");
        String updateName = scanner.nextLine().trim();
        if (updateName.isEmpty()) {
            System.out.println(Utilities.RED + "Pet name cannot be empty." + Utilities.RESET);
            return;
        }

        // Check if the pet exists
        if (!petManager.isPetNameExists(updateName)) {
            System.out.println(Utilities.RED + "Pet named " + updateName + " not found." + Utilities.RESET);
            return;
        }

        int updateAge = -1;
        while (updateAge <= 0) {
            System.out.print("Enter pet's new age (positive integer): ");
            String ageInput = scanner.nextLine().trim();
            try {
                updateAge = Integer.parseInt(ageInput);
                if (updateAge <= 0) {
                    System.out.println(Utilities.RED + "Age must be greater than zero. Please re-enter!" + Utilities.RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(Utilities.RED + "Invalid input, please enter an integer!" + Utilities.RESET);
            }
        }

        String updateGender = "";
        while (true) {
            System.out.print("Enter pet's new gender (m/f): ");
            String genderInput = scanner.nextLine().trim();
            try {
                updateGender = Utilities.convertMaleFemaleToGender(genderInput);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(Utilities.RED + e.getMessage() + Utilities.RESET);
            }
        }

        String updateBreed = "";
        while (updateBreed.isEmpty()) {
            System.out.print("Enter pet's new breed: ");
            updateBreed = scanner.nextLine().trim();
            if (updateBreed.isEmpty()) {
                System.out.println(Utilities.RED + "Breed cannot be empty. Please re-enter." + Utilities.RESET);
            }
        }

        petManager.updatePetInfo(updateName, updateAge, updateGender, updateBreed);
    }

    // Handles the process of searching for a pet
    private static void searchPetFlow(PetManager petManager, Scanner scanner) {
        System.out.print("Enter the name of the pet to search: ");
        String searchName = scanner.nextLine().trim();
        if (searchName.isEmpty()) {
            System.out.println(Utilities.RED + "Pet name cannot be empty." + Utilities.RESET);
            return;
        }
        petManager.searchPetByName(searchName);
    }

    // Handles the process of adopting a pet
    private static void adoptPetFlow(PetManager petManager, Scanner scanner) {
        System.out.println("\n" + Utilities.PURPLE + "Current Pets:" + Utilities.RESET);
        petManager.showAllPets();

        System.out.print("Enter the name of the pet you want to adopt: ");
        String adoptedName = scanner.nextLine().trim();
        if (adoptedName.isEmpty()) {
            System.out.println(Utilities.RED + "Pet name cannot be empty." + Utilities.RESET);
            return;
        }
        petManager.adoptPet(adoptedName, scanner);
    }

    // Handles the process of partial match searching
    private static void partialSearchFlow(PetManager petManager, Scanner scanner) {
        System.out.print("Enter part of the pet's name to search: ");
        String partialName = scanner.nextLine().trim();
        if (partialName.isEmpty()) {
            System.out.println(Utilities.RED + "Search content cannot be empty." + Utilities.RESET);
            return;
        }
        petManager.searchPetsByPartialName(partialName);
    }

    // Handles the process of filtering pets
    private static void filterPetsFlow(PetManager petManager, Scanner scanner) {
        System.out.println("\n" + Utilities.BLUE + "Choose filter criteria:" + Utilities.RESET);
        System.out.println("1. Filter by Breed");
        System.out.println("2. Filter by Adoption Status");
        System.out.print(Utilities.BLUE + "Enter your choice (1-2): " + Utilities.RESET);
        int filterChoice = -1;
        while (filterChoice < 1 || filterChoice > 2) {
            try {
                String input = scanner.nextLine().trim();
                filterChoice = Integer.parseInt(input);
                if (filterChoice < 1 || filterChoice > 2) {
                    System.out.print(Utilities.RED + "Invalid option, please enter 1 or 2: " + Utilities.RESET);
                }
            } catch (NumberFormatException e) {
                System.out.print(Utilities.RED + "Invalid input, please enter a number (1 or 2): " + Utilities.RESET);
            }
        }

        switch (filterChoice) {
            case 1:
                filterByBreedFlow(petManager, scanner);
                break;
            case 2:
                filterByAdoptionStatusFlow(petManager, scanner);
                break;
            default:
                System.out.println(Utilities.RED + "Invalid option." + Utilities.RESET);
        }
    }

    // Handles the process of filtering pets by breed
    private static void filterByBreedFlow(PetManager petManager, Scanner scanner) {
        Set<String> allBreeds = petManager.getAllBreeds();
        if (allBreeds.isEmpty()) {
            System.out.println(Utilities.YELLOW + "No pets available to filter by breed." + Utilities.RESET);
            return;
        }

        System.out.println("\n" + Utilities.GREEN + "Available Breeds:" + Utilities.RESET);
        for (String breed : allBreeds) {
            System.out.println("- " + breed);
        }

        System.out.print("Enter the breed to filter: ");
        String breed = scanner.nextLine().trim();
        if (breed.isEmpty()) {
            System.out.println(Utilities.RED + "Breed cannot be empty." + Utilities.RESET);
            return;
        }
        petManager.filterPetsByBreed(breed);
    }

    // Handles the process of filtering pets by adoption status using y/n
    private static void filterByAdoptionStatusFlow(PetManager petManager, Scanner scanner) {
        boolean isAdopted = false;
        while (true) {
            System.out.print("Select adoption status (y. Adopted, n. Not Adopted): ");
            String statusInput = scanner.nextLine().trim().toLowerCase();
            try {
                isAdopted = Utilities.convertYesNoToBoolean(statusInput);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(Utilities.RED + "Invalid input, please enter 'y' or 'n'." + Utilities.RESET);
            }
        }
        petManager.filterPetsByAdoptionStatus(isAdopted);
    }
}

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class PetManagerTest {

    private static final String TEST_DATA_FILE = "test_pets.csv";
    private PetManager petManager;

    @BeforeEach
    void setUp() {
        // Ensure the test data file is clean before each test
        try {
            Files.deleteIfExists(Paths.get(TEST_DATA_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
        petManager = new PetManager(TEST_DATA_FILE);
    }

    @AfterEach
    void tearDown() {
        // Delete the test data file after each test
        try {
            Files.deleteIfExists(Paths.get(TEST_DATA_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testAddPet_Success() {
        Pet pet = new Pet("Buddy", 3, "male", "Labrador", false, "", "");
        assertTrue(petManager.addPet(pet));
        assertTrue(petManager.isPetNameExists("Buddy"));
    }

    @Test
    void testAddPet_DuplicateName() {
        Pet pet1 = new Pet("Buddy", 3, "male", "Labrador", false, "", "");
        Pet pet2 = new Pet("Buddy", 2, "female", "Beagle", false, "", "");
        assertTrue(petManager.addPet(pet1));
        assertFalse(petManager.addPet(pet2));
    }

    @Test
    void testDeletePetByName_Success() {
        Pet pet = new Pet("Charlie", 4, "female", "Poodle", false, "", "");
        petManager.addPet(pet);
        assertTrue(petManager.isPetNameExists("Charlie"));
        petManager.deletePetByName("Charlie");
        assertFalse(petManager.isPetNameExists("Charlie"));
    }

    @Test
    void testDeletePetByName_NotFound() {
        petManager.deletePetByName("NonExistentPet");
        // No exception should be thrown, and method should handle gracefully
        assertFalse(petManager.isPetNameExists("NonExistentPet"));
    }

    @Test
    void testUpdatePetInfo_Success() {
        Pet pet = new Pet("Daisy", 2, "female", "Bulldog", false, "", "");
        petManager.addPet(pet);
        petManager.updatePetInfo("Daisy", 3, "female", "French Bulldog");

        // Verify updates
        List<Pet> pets = getPetsFromManager(petManager);
        assertEquals(1, pets.size());
        Pet updatedPet = pets.get(0);
        assertEquals(3, updatedPet.getAge());
        assertEquals("female", updatedPet.getGender());
        assertEquals("French Bulldog", updatedPet.getBreed());
    }

    @Test
    void testUpdatePetInfo_NotFound() {
        petManager.updatePetInfo("Ghost", 5, "male", "Unknown");
        // Since the pet doesn't exist, nothing should happen
        assertFalse(petManager.isPetNameExists("Ghost"));
    }

    @Test
    void testSearchPetByName_Found() {
        Pet pet = new Pet("Ella", 1, "female", "Chihuahua", false, "", "");
        petManager.addPet(pet);

        // Redirect System.out to capture the output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        petManager.searchPetByName("Ella");

        String expectedOutput = Utilities.GREEN + "Found pet: " + pet + Utilities.RESET + System.lineSeparator();
        assertTrue(outContent.toString().contains(expectedOutput));

        // Reset System.out
        System.setOut(System.out);
    }

    @Test
    void testSearchPetByName_NotFound() {
        // Redirect System.out to capture the output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        petManager.searchPetByName("Ghost");

        String expectedOutput = Utilities.RED + "Pet named Ghost not found." + Utilities.RESET + System.lineSeparator();
        assertTrue(outContent.toString().contains(expectedOutput));

        // Reset System.out
        System.setOut(System.out);
    }

    @Test
    void testSearchPetsByPartialName_Found() {
        Pet pet1 = new Pet("Maxwell", 5, "male", "German Shepherd", false, "", "");
        Pet pet2 = new Pet("Maxine", 2, "female", "Siamese", false, "", "");
        Pet pet3 = new Pet("Bella", 3, "female", "Persian", false, "", "");
        petManager.addPet(pet1);
        petManager.addPet(pet2);
        petManager.addPet(pet3);

        // Redirect System.out to capture the output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        petManager.searchPetsByPartialName("Max");

        String expectedOutput1 = Utilities.GREEN + "Found the following pets:" + Utilities.RESET + System.lineSeparator();
        String expectedOutput2 = pet1.toString() + System.lineSeparator();
        String expectedOutput3 = pet2.toString() + System.lineSeparator();
        assertTrue(outContent.toString().contains(expectedOutput1));
        assertTrue(outContent.toString().contains(expectedOutput2));
        assertTrue(outContent.toString().contains(expectedOutput3));

        // Reset System.out
        System.setOut(System.out);
    }

    @Test
    void testSearchPetsByPartialName_NotFound() {
        Pet pet = new Pet("Luna", 4, "female", "Husky", false, "", "");
        petManager.addPet(pet);

        // Redirect System.out to capture the output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        petManager.searchPetsByPartialName("xyz");

        String expectedOutput = Utilities.RED + "No pets found containing \"xyz\"." + Utilities.RESET + System.lineSeparator();
        assertTrue(outContent.toString().contains(expectedOutput));

        // Reset System.out
        System.setOut(System.out);
    }

    @Test
    void testFilterPetsByBreed_Found() {
        Pet pet1 = new Pet("Oliver", 2, "male", "Beagle", false, "", "");
        Pet pet2 = new Pet("Lily", 3, "female", "Beagle", false, "", "");
        Pet pet3 = new Pet("Molly", 1, "female", "Poodle", false, "", "");
        petManager.addPet(pet1);
        petManager.addPet(pet2);
        petManager.addPet(pet3);

        // Redirect System.out to capture the output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        petManager.filterPetsByBreed("Beagle");

        String expectedOutput1 = Utilities.GREEN + "Pets with breed \"Beagle\":" + Utilities.RESET + System.lineSeparator();
        String expectedOutput2 = pet1.toString() + System.lineSeparator();
        String expectedOutput3 = pet2.toString() + System.lineSeparator();
        assertTrue(outContent.toString().contains(expectedOutput1));
        assertTrue(outContent.toString().contains(expectedOutput2));
        assertTrue(outContent.toString().contains(expectedOutput3));
        assertFalse(outContent.toString().contains(pet3.toString()));

        // Reset System.out
        System.setOut(System.out);
    }

    @Test
    void testFilterPetsByBreed_NotFound() {
        Pet pet = new Pet("Sophie", 3, "female", "Sphynx", false, "", "");
        petManager.addPet(pet);

        // Redirect System.out to capture the output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        petManager.filterPetsByBreed("Beagle");

        String expectedOutput = Utilities.RED + "No pets found with breed \"Beagle\"." + Utilities.RESET + System.lineSeparator();
        assertTrue(outContent.toString().contains(expectedOutput));

        // Reset System.out
        System.setOut(System.out);
    }

    @Test
    void testFilterPetsByAdoptionStatus_Found() {
        Pet pet1 = new Pet("Rocky", 4, "male", "Boxer", true, "John Doe", "1234567890");
        Pet pet2 = new Pet("Maggie", 2, "female", "Bulldog", false, "", "");
        petManager.addPet(pet1);
        petManager.addPet(pet2);

        // Test for adopted pets
        ByteArrayOutputStream outContentAdopted = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContentAdopted));

        petManager.filterPetsByAdoptionStatus(true);

        String expectedOutput1 = Utilities.GREEN + "Adopted pets:" + Utilities.RESET + System.lineSeparator();
        String expectedOutput2 = pet1.toString() + System.lineSeparator();
        assertTrue(outContentAdopted.toString().contains(expectedOutput1));
        assertTrue(outContentAdopted.toString().contains(expectedOutput2));
        assertFalse(outContentAdopted.toString().contains(pet2.toString()));

        // Test for available pets
        ByteArrayOutputStream outContentAvailable = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContentAvailable));

        petManager.filterPetsByAdoptionStatus(false);

        String expectedOutput3 = Utilities.GREEN + "Available pets for adoption:" + Utilities.RESET + System.lineSeparator();
        String expectedOutput4 = pet2.toString() + System.lineSeparator();
        assertTrue(outContentAvailable.toString().contains(expectedOutput3));
        assertTrue(outContentAvailable.toString().contains(expectedOutput4));
        assertFalse(outContentAvailable.toString().contains(pet1.toString()));

        // Reset System.out
        System.setOut(System.out);
    }

    @Test
    void testFilterPetsByAdoptionStatus_NotFound() {
        Pet pet = new Pet("Bella", 5, "female", "Labrador", false, "", "");
        petManager.addPet(pet);

        // Redirect System.out to capture the output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        petManager.filterPetsByAdoptionStatus(true);

        String expectedOutput = Utilities.YELLOW + "No adopted pets." + Utilities.RESET + System.lineSeparator();
        assertTrue(outContent.toString().contains(expectedOutput));

        // Reset System.out
        System.setOut(System.out);
    }

    @Test
    void testAdoptPet_Success() {
        Pet pet = new Pet("Luna", 3, "female", "Siberian Husky", false, "", "");
        petManager.addPet(pet);

        // Mock user input using Scanner with predefined inputs
        String adopterName = "Alice";
        String adopterContact = "0987654321";
        String input = adopterName + "\n" + adopterContact + "\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        // Redirect System.out to capture the output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        petManager.adoptPet("Luna", scanner);

        // Verify pet adoption status and adopter info
        List<Pet> pets = getPetsFromManager(petManager);
        assertEquals(1, pets.size());
        Pet adoptedPet = pets.get(0);
        assertTrue(adoptedPet.isAdopted());
        assertEquals(adopterName, adoptedPet.getAdopterName());
        assertEquals(adopterContact, adoptedPet.getAdopterContact());

        String expectedOutput = Utilities.GREEN + "Congratulations! You have successfully adopted Luna." + Utilities.RESET + System.lineSeparator();
        assertTrue(outContent.toString().contains(expectedOutput));

        // Reset System.out
        System.setOut(System.out);
    }

    @Test
    void testAdoptPet_AlreadyAdopted() {
        Pet pet = new Pet("Duke", 4, "male", "Doberman", true, "Bob Smith", "1122334455");
        petManager.addPet(pet);

        // Mock user input (no adopter info since pet is already adopted)
        String input = "\n"; // No input needed
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        // Redirect System.out to capture the output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        petManager.adoptPet("Duke", scanner);

        String expectedOutput = Utilities.RED + "This pet has already been adopted!" + Utilities.RESET + System.lineSeparator();
        assertTrue(outContent.toString().contains(expectedOutput));

        // Reset System.out
        System.setOut(System.out);
    }

    @Test
    void testAdoptPet_NotFound() {
        // Mock user input (no adopter info since pet is not found)
        String input = "\n"; // No input needed
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

        // Redirect System.out to capture the output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        petManager.adoptPet("Ghost", scanner);

        String expectedOutput = Utilities.RED + "Pet named Ghost not found." + Utilities.RESET + System.lineSeparator();
        assertTrue(outContent.toString().contains(expectedOutput));

        // Reset System.out
        System.setOut(System.out);
    }

    @Test
    void testSaveAndLoadData() {
        Pet pet1 = new Pet("Oscar", 2, "male", "Beagle", false, "", "");
        Pet pet2 = new Pet("Lucy", 1, "female", "Persian", true, "David Lee", "6677889900");
        petManager.addPet(pet1);
        petManager.addPet(pet2);

        // Save data
        petManager.saveData();

        // Create a new PetManager instance to load data
        PetManager newPetManager = new PetManager(TEST_DATA_FILE);
        List<Pet> loadedPets = getPetsFromManager(newPetManager);

        assertEquals(2, loadedPets.size());

        Pet loadedPet1 = loadedPets.get(0);
        Pet loadedPet2 = loadedPets.get(1);

        assertEquals("Oscar", loadedPet1.getName());
        assertEquals(2, loadedPet1.getAge());
        assertEquals("male", loadedPet1.getGender());
        assertEquals("Beagle", loadedPet1.getBreed());
        assertFalse(loadedPet1.isAdopted());

        assertEquals("Lucy", loadedPet2.getName());
        assertEquals(1, loadedPet2.getAge());
        assertEquals("female", loadedPet2.getGender());
        assertEquals("Persian", loadedPet2.getBreed());
        assertTrue(loadedPet2.isAdopted());
        assertEquals("David Lee", loadedPet2.getAdopterName());
        assertEquals("6677889900", loadedPet2.getAdopterContact());
    }

    // Helper method to access the private pets list via reflection
    private List<Pet> getPetsFromManager(PetManager manager) {
        try {
            java.lang.reflect.Field petsField = PetManager.class.getDeclaredField("pets");
            petsField.setAccessible(true);
            return (List<Pet>) petsField.get(manager);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}

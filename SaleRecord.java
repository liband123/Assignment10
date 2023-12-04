import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

public class SaleRecord implements Comparable<SaleRecord> {
    ChronoLocalDate saleDate;
    String salesperson;
    String customerName;
    String carMake;
    String carModel;
    int carYear;
    double salePrice;
    double commissionRate;
    double commissionEarned;

    public SaleRecord(LocalDate saleDate, String salesperson, String customerName, 
                      String carMake, String carModel, int carYear, 
                      double salePrice, double commissionRate, double commissionEarned) {
        this.saleDate = saleDate;
        this.salesperson = salesperson;
        this.customerName = customerName;
        this.carMake = carMake;
        this.carModel = carModel;
        this.carYear = carYear;
        this.salePrice = salePrice;
        this.commissionRate = commissionRate;
        this.commissionEarned = commissionEarned;
    }
	public ChronoLocalDate getSaleDate() {
		return saleDate;
	}

	public String getSalesperson() {
		return salesperson;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getCarMake() {
		return carMake;
	}

	public String getCarModel() {
		return carModel;
	}

	public int getCarYear() {
		return carYear;
	}

	public double getSalePrice() {
		return salePrice;
	}

	public double getCommissionRate() {
		return commissionRate;
	}

	public double getCommissionEarned() {
		return commissionEarned;
	}
	// Getter for the customer's last name
	public String getCustomerLastName() {
		return extractLastName(this.customerName);
	}

	// Getter for the salesperson's last name
	public String getSalespersonLastName() {
		return extractLastName(this.salesperson);
	}

	// Helper method to extract the last name from a full name
	private static String extractLastName(String fullName) {
		String[] parts = fullName.split(" ");
		return parts[parts.length - 1];
	}
	
	@Override
    public int compareTo(SaleRecord other) {
        // Sort by date in descending order
        return other.saleDate.compareTo(this.saleDate);
    }
}




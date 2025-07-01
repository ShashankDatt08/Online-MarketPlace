public class BidService {
    // Existing methods...

    /**
     * Deletes all bids created on or before the specified date.
     * @param date The date to compare bids against.
     */
    public void deleteBidsBeforeDate(Date date) {
        // Logic to delete bids from the database
        // Example: DELETE FROM bids WHERE creation_date <= date
    }
}
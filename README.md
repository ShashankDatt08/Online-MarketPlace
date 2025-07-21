# OnlineMarketPlace
Online MarketPlace for Freelancers

## API Endpoints

### Delete Bids Before Date

Deletes all bids placed before a specified date (start of the day).

```
DELETE /bid/delete/{date}
```

**Path Parameter:**
- `date` – cutoff date in ISO format `yyyy-MM-dd` (e.g., `2023-08-01`)

**Response:**
- `200 OK` with message `Bids deleted successfully` on success.

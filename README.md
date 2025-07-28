# OnlineMarketPlace
Online MarketPlace for Freelancers

## Bid Deletion Endpoint

To delete bids submitted before a given date, send a DELETE request to the following endpoint:

```
DELETE /bid/delete/{date}
```

Where `{date}` is the cutoff in ISO-8601 format (e.g., `2025-01-01T00:00:00`).

# OnlineMarketPlace
Online MarketPlace for Freelancers

## Bid Deletion by Date

A new DELETE endpoint is available to remove all bids placed before a specified date and time.

```http
DELETE /bid/delete/{date}
```

The `{date}` path parameter should be an ISO-8601 date-time string (e.g., `2023-07-13T15:30:00`).
On success, the service responds with a confirmation message.

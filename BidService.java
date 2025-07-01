class BidService:
    def __init__(self):
        self.bids = {}

    def create_bid(self, bid_id, bid_data):
        self.bids[bid_id] = bid_data

    def get_bid(self, bid_id):
        return self.bids.get(bid_id)

    def delete_bid(self, bid_id):
        if bid_id in self.bids:
            del self.bids[bid_id]
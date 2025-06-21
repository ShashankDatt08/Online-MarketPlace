package com.marketplace.onlinemarketplace.service;


import com.marketplace.onlinemarketplace.entity.Bid;
import com.marketplace.onlinemarketplace.entity.Conversation;
import com.marketplace.onlinemarketplace.entity.Projects;
import com.marketplace.onlinemarketplace.entity.User;
import com.marketplace.onlinemarketplace.repository.BidRepo;
import com.marketplace.onlinemarketplace.mongoRepo.ConversationRepo;
import com.marketplace.onlinemarketplace.repository.ProjectRepo;
import com.marketplace.onlinemarketplace.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BidService {

    @Autowired
    private BidRepo bidRepo;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ConversationRepo conversationRepo;

    public Bid createBids(Long projectId, Long freelancerId, Long amount, String proposal) {
        Projects project = projectRepo.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));

        if(project.getStatus() == Projects.Status.CLOSED || project.getStatus() == Projects.Status.INPROGRESS){
            throw new RuntimeException("Can't place bids as the project is not available");
        }

        Optional<User> user = userRepo.findById(freelancerId);
        if (user.get().getRole() != User.Role.FREELANCER) {
            throw new RuntimeException("Only Freelancers can place bids");
        }

        Bid bid = new Bid();
        bid.setProjectId(projectId);
        bid.setFreelancerId(freelancerId);
        bid.setAmount(amount);
        bid.setProposal(proposal);
        bid.setBidDate(LocalDateTime.now());
        bid.setStatus(Bid.BidStatus.PENDING);

        return bidRepo.save(bid);

    }


    public List<Bid> getBidById(Long projectId, boolean isClientViewing) {
        List<Bid> bids = bidRepo.findByProjectId(projectId);

        if (!isClientViewing) {
            bids.forEach(bid -> {
                bid.setFreelancerId(null);
                bid.setFreelancerName("Anonymous User");
            });

        }
        return bids;
    }

    public Bid acceptBid(Long bidId, Long clientId) {
        Bid bid = bidRepo.findById(bidId).orElseThrow(() -> new RuntimeException("Bid not found"));
        Projects projects = projectRepo.findById(bid.getProjectId()).orElseThrow(() -> new RuntimeException("Project not found"));

        if (!projects.getClientId().equals(clientId)) {
            throw new RuntimeException("Only Client who posted can accept bid");
        }

        List<Bid> exisitingBids = bidRepo.findByProjectIdAndStatus(bid.getProjectId(), Bid.BidStatus.ACCEPTED);
        if (!exisitingBids.isEmpty()) {
            throw new RuntimeException("Bid already accepted for this project");
        }
        bid.setStatus(Bid.BidStatus.ACCEPTED);
        projects.setStatus(Projects.Status.INPROGRESS);
        bidRepo.save(bid);

        List<Bid> otherBids = bidRepo.findByProjectIdAndStatus(bid.getProjectId(), Bid.BidStatus.PENDING);
        for (Bid otherBid : otherBids) {
            if (!otherBid.getId().equals(bidId)) {
                otherBid.setStatus(Bid.BidStatus.REJECTED);
                bidRepo.save(otherBid);
            }

        }
        return bid;
    }

    using Microsoft.AspNetCore.Mvc;
    using System.Threading.Tasks;

    namespace YourNamespace.Controllers
    {
        [Route("api/[controller]")]
        [ApiController]
        public class RegistrationController : ControllerBase
        {
            private readonly IUserService _userService;

            public RegistrationController(IUserService userService)
            {
                _userService = userService;
            }


            [HttpPost("changepassword")]
            public async Task<IActionResult> ChangePassword(ChangePasswordModel model)
            {
                if (!ModelState.IsValid)
                {
                    return BadRequest(ModelState);
                }

                var result = await _userService.ChangePasswordAsync(model);

                if (!result.Succeeded)
                {
                    return BadRequest(result.Errors);
                }

                return Ok();
            }
        }
    }
    ```

    ```csharp
    using System.ComponentModel.DataAnnotations;

    namespace YourNamespace.Models
    {
        public class ChangePasswordModel
        {
            [Required]
            public string UserId { get; set; }

            [Required]
            [DataType(DataType.Password)]
            public string OldPassword { get; set; }

            [Required]
            [DataType(DataType.Password)]
            public string NewPassword { get; set; }
        }
    }
    ```

    ```csharp
    using System.Threading.Tasks;
    using YourNamespace.Models;
    using Microsoft.AspNetCore.Identity;

    namespace YourNamespace.Services
    {
        public interface IUserService
        {
            Task<IdentityResult> ChangePasswordAsync(ChangePasswordModel model);
        }
    }
    ```

    ```csharp
    using System.Threading.Tasks;
    using YourNamespace.Models;
    using Microsoft.AspNetCore.Identity;

    namespace YourNamespace.Services
    {
        public class UserService : IUserService
        {
            private readonly UserManager<IdentityUser> _userManager;

            public UserService(UserManager<IdentityUser> userManager)
            {
                _userManager = userManager;
            }

            public async Task<IdentityResult> ChangePasswordAsync(ChangePasswordModel model)
            {
                var user = await _userManager.FindByIdAsync(model.UserId);
                if (user == null)
                {
                    return IdentityResult.Failed(new IdentityError { Description = $"User with ID '{model.UserId}' not found." });
                }

                return await _userManager.ChangePasswordAsync(user, model.OldPassword, model.NewPassword);
            }
        }
    }

}

package codeu.chat.common;


import java.util.ArrayList;

import codeu.chat.common.ConversationHeader;


public final class  UserFollowing {
  private ArrayList<ConversationHeader> createdConversations = new ArrayList<ConversationHeader>();
  private ArrayList<ConversationHeader> joinedConversations = new ArrayList<ConversationHeader>();
  public final User userFollower;
  public final User userBeingFollowed;

  public UserFollowing (User userFollower, User userBeingFollowed) {
    this.userFollower = userFollower;
    this.userBeingFollowed = userBeingFollowed;
  }

  public String statusUpdate() {
    StringBuilder status = new StringBuilder();
    for (ConversationHeader conversation : createdConversations) {
      String title = conversation.title;
      String line = String.format("USER %s created CONVERSATION %s!\n", userBeingFollowed.name, title);
      status.append(line);
    }
    for (ConversationHeader conversation : joinedConversations) {
      String title = conversation.title;
      String line = String.format("USER %s joined CONVERSATION %s!\n", userBeingFollowed.name, title);
      status.append(line);
    }
    createdConversations.clear();
    joinedConversations.clear();
    return status.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
       return true;
    if (obj == null)
       return false;
    if (getClass() != obj.getClass())
       return false;
    UserFollowing that = (UserFollowing) obj;
    return (this.userFollower == that.userFollower && this.userBeingFollowed == that.userBeingFollowed);
  }

  public void addCreatedConversation(ConversationHeader conversation) {
    createdConversations.add(conversation);
  }

  public void addJoinedConversation(ConversationHeader conversation) {
    joinedConversations.add(conversation);
  }
}

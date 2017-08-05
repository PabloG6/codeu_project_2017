// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package codeu.chat.server;

import java.util.Collection;
import java.util.HashMap;

import codeu.chat.common.BasicController;
import codeu.chat.common.ConversationHeader;
import codeu.chat.common.ConversationPayload;
import codeu.chat.common.Message;
import codeu.chat.common.RandomUuidGenerator;
import codeu.chat.common.RawController;
import codeu.chat.common.User;
import codeu.chat.util.Logger;
import codeu.chat.util.Time;
import codeu.chat.util.Uuid;
import codeu.chat.util.store.Store;

public final class Controller implements RawController, BasicController {

  private final static Logger.Log LOG = Logger.newLog(Controller.class);

  private final Model model;
  private final Uuid.Generator uuidGenerator;

  public Controller(Uuid serverId, Model model) {
    this.model = model;
    this.uuidGenerator = new RandomUuidGenerator(serverId, System.currentTimeMillis());
  }

  @Override
  public Message newMessage(Uuid author, Uuid conversation, String body) {
    return newMessage(createId(), author, conversation, body, Time.now());
  }

  @Override
  public User newUser(String name) {
    return newUser(createId(), name, Time.now());
  }

  @Override
  public ConversationHeader newConversation(String title, Uuid owner, int defaultPermission) {
    return newConversation(createId(), owner, Time.now(), title, defaultPermission);
  }

  @Override
  public Message newMessage(Uuid id, Uuid author, Uuid conversation, String body, Time creationTime) {

    final User foundUser = model.userById().first(author);
    final ConversationPayload foundConversation = model.conversationPayloadById().first(conversation);

    Message message = null;

    if (foundUser != null && foundConversation != null && isIdFree(id)) {

      message = new Message(id, Uuid.NULL, Uuid.NULL, creationTime, author, body);
      model.add(message);
      LOG.info("Message added: %s", message.id);

      // Find and update the previous "last" message so that it's "next" value
      // will point to the new message.

      if (Uuid.equals(foundConversation.lastMessage, Uuid.NULL)) {

        // The conversation has no messages in it, that's why the last message is NULL (the first
        // message should be NULL too. Since there is no last message, then it is not possible
        // to update the last message's "next" value.

      } else {
        final Message lastMessage = model.messageById().first(foundConversation.lastMessage);
        lastMessage.next = message.id;
      }

      // If the first message points to NULL it means that the conversation was empty and that
      // the first message should be set to the new message. Otherwise the message should
      // not change.

      foundConversation.firstMessage =
          Uuid.equals(foundConversation.firstMessage, Uuid.NULL) ?
          message.id :
          foundConversation.firstMessage;

      // Update the conversation to point to the new last message as it has changed.

      foundConversation.lastMessage = message.id;
    }

    return message;
  }

  @Override
  public User newUser(Uuid id, String name, Time creationTime) {

    User user = null;

    if (isIdFree(id)) {

      user = new User(id, name, creationTime);
      model.add(user);

      LOG.info(
          "newUser success (user.id=%s user.name=%s user.time=%s)",
          id,
          name,
          creationTime);

    } else {

      LOG.info(
          "newUser fail - id in use (user.id=%s user.name=%s user.time=%s)",
          id,
          name,
          creationTime);
    }

    return user;
  }

  @Override
  public ConversationHeader newConversation(Uuid id, Uuid owner, Time creationTime, String title, int defaultPermission) {

    final User foundOwner = model.userById().first(owner);

    ConversationHeader conversation = null;

    if (foundOwner != null && isIdFree(id)) {
      conversation = new ConversationHeader(id, owner, creationTime, title, defaultPermission);
      model.add(foundOwner, conversation);
      LOG.info("Conversation added: " + id);
    }

    return conversation;
  }

  private Uuid createId() {

    Uuid candidate;

    for (candidate = uuidGenerator.make();
         isIdInUse(candidate);
         candidate = uuidGenerator.make()) {

     // Assuming that "randomUuid" is actually well implemented, this
     // loop should never be needed, but just incase make sure that the
     // Uuid is not actually in use before returning it.

    }

    return candidate;
  }

  private boolean isIdInUse(Uuid id) {
    return model.messageById().first(id) != null ||
           model.conversationById().first(id) != null ||
           model.userById().first(id) != null;
  }

  private boolean isIdFree(Uuid id) { return !isIdInUse(id); }

  private final HashMap<Uuid, HashMap<Uuid, Integer>> userConversationTracking = new HashMap<Uuid, HashMap<Uuid, Integer>>();
  
  public String newStatusUpdate(Uuid user) {
    StringBuilder status = new StringBuilder();
    HashMap<Uuid, Integer> userConversationSize = userConversationTracking.get(user);
    for (Uuid conversation : userConversationSize.keySet()) {
      ConversationHeader convo = model.conversationById().first(conversation);
      String title = convo.title;
      int newMessages = convo.size - userConversationSize.get(conversation);
      String line = String.format("CONVERSATION %s: You have %d new messages!\n", title, newMessages);
      status.append(line);
      userConversationTracking.get(user).put(conversation, convo.size);
    }
    User userA = model.userById().first(user);
    status.append(userA.statusUpdate());
    return status.toString();
  }

  public void unfollowUser(User userA, User userB) {
    User user1 = model.userById().first(userA.id);
    User user2 = model.userById().first(userB.id);
    User.unfollow(user1, user2);
  }

  public void followUser(User userA, User userB) {
    User user1 = model.userById().first(userA.id);
    User user2 = model.userById().first(userB.id);
    User.follow(user1, user2);
  }

  public void unfollowConversation(Uuid user, Uuid conversation) {
    userConversationTracking.get(user).remove(conversation);
  }

  public void followConversation(Uuid user, Uuid conversation) {
    // Put into hashmap the conversation and what the size of the conversation
    // is for the user at the time of following
    ConversationHeader convo = model.conversationById().first(conversation);
    userConversationTracking.get(user).put(conversation, convo.size);
  }
  
  //change user's status for a conversation (member/owner/creator)
  public void changePermission(Uuid user, Uuid conversation, int newPermission) {
	ConversationHeader convo = model.conversationById().first(conversation);
	convo.userLevels.remove(user);
	convo.userLevels.put(user, newPermission); 
  }

}

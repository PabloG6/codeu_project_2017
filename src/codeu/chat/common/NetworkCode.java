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

package codeu.chat.common;

public final class NetworkCode {

  public static final int
      NO_MESSAGE = 0,
      GET_USERS_REQUEST = 1,
      GET_USERS_RESPONSE = 2,
      GET_ALL_CONVERSATIONS_REQUEST = 3,
      GET_ALL_CONVERSATIONS_RESPONSE = 4,
      GET_CONVERSATIONS_BY_ID_RESPONSE = 5,
      GET_CONVERSATIONS_BY_ID_REQUEST = 6,
      GET_MESSAGES_BY_ID_REQUEST = 7,
      GET_MESSAGES_BY_ID_RESPONSE = 8,
      NEW_MESSAGE_REQUEST = 9,
      NEW_MESSAGE_RESPONSE = 10,
      NEW_USER_REQUEST = 11,
      NEW_USER_RESPONSE = 12,
      NEW_CONVERSATION_REQUEST = 13,
      NEW_CONVERSATION_RESPONSE = 14,
      RELAY_READ_REQUEST = 27,
      RELAY_READ_RESPONSE = 28,
      RELAY_WRITE_REQUEST = 29,
      RELAY_WRITE_RESPONSE = 30,
      SERVER_INFO_REQUEST = 31,
      SERVER_INFO_RESPONSE = 32,
      GET_SERVER_UPTIME_REQUEST = 33,
      GET_SERVER_UPTIME_RESPONSE = 34,
      NEW_FOLLOW_CONVERSATION_REQUEST = 35,
      NEW_FOLLOW_CONVERSATION_RESPONSE = 36,
      NEW_UNFOLLOW_CONVERSATION_REQUEST = 37,
      NEW_UNFOLLOW_CONVERSATION_RESPONSE = 38,
      NEW_FOLLOW_USER_REQUEST = 39,
      NEW_FOLLOW_USER_RESPONSE = 40,
      NEW_UNFOLLOW_USER_REQUEST = 41,
      NEW_UNFOLLOW_USER_RESPONSE = 42,
      NEW_STATUS_UPDATE_REQUEST = 43,
      NEW_STATUS_UPDATE_RESPONSE = 44;
      CONVERSATION_ACCESS_DENIED = 45;
}

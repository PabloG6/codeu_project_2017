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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

import codeu.chat.util.Serializer;
import codeu.chat.util.Serializers;
import codeu.chat.util.Time;
import codeu.chat.util.Uuid;

public final class ConversationHeader {

  public static final Serializer<ConversationHeader> SERIALIZER = new Serializer<ConversationHeader>() {

    @Override
    public void write(OutputStream out, ConversationHeader value) throws IOException {

      Uuid.SERIALIZER.write(out, value.id);
      Uuid.SERIALIZER.write(out, value.owner);
      Time.SERIALIZER.write(out, value.creation);
      Serializers.STRING.write(out, value.title);
      Serializers.INTEGER.write(out, value.defaultByte);

    }

    @Override
    public ConversationHeader read(InputStream in) throws IOException {

      return new ConversationHeader(
          Uuid.SERIALIZER.read(in),
          Uuid.SERIALIZER.read(in),
          Time.SERIALIZER.read(in),
          Serializers.STRING.read(in),
          Serializers.INTEGER.read(in)
      );

    }
  };

  // permission levels
  // member == 1
  // owner == 2
  // creator == 3
  
  public enum permissions {
    member(1), owner(2), creator(3);
	private int permissionLevel;
	
	private permissions(int permissionLevel) {
	  this.permissionLevel = permissionLevel;
	}
	
	public int getPermissionLevel() {
	  return permissionLevel;
	}
  }
 
  public final Uuid id;
  public final Uuid owner;
  public final Time creation;
  public final String title;
  public final Integer defaultByte;
  public final HashMap<Uuid, Integer> userLevels;

  public ConversationHeader(Uuid id, Uuid owner, Time creation, String title, Integer defaultByte) {

    this.id = id;
    this.owner = owner;
    this.creation = creation;
    this.title = title;
    this.defaultByte = defaultByte;
    this.userLevels = new HashMap<Uuid, Integer>();

    // set creator bit
    
    permissions creatorLevel = permissions.creator; 
    userLevels.put(owner, creatorLevel.getPermissionLevel());
  }
  
  // set default level of a user
  
  public void setDefault(Uuid user) {
	if(userLevels.get(user) == null) {
	  userLevels.put(user, defaultByte);
	}
  }
  
  // check the permission level of a user

  // check if user is member
  
  public boolean isMember(Uuid user) {
	if(userLevels.get(user) != null) {
	  return userLevels.get(user) >= 1;
	}
	return defaultByte >= 1;
  }

  // check if user is owner
  
  public boolean isOwner(Uuid user) {
	if(userLevels.get(user) != null) {
	  return userLevels.get(user) >= 2;
	}
	return defaultByte >= 2;
  }

  // check if user is creator
  
  public boolean isCreator(Uuid user) {
	if(userLevels.get(user) != null) {
	  return userLevels.get(user) == 3;
	}
	return defaultByte == 3;
  }

}
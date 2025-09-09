from glide_sync import GlideClientConfiguration, NodeAddress, GlideClient
from mcp.server.elicitation import AcceptedElicitation, DeclinedElicitation, CancelledElicitation
from mcp.server.fastmcp import FastMCP, Context
from pydantic import BaseModel, Field
from typing import Optional

import json
import uuid

mcp = FastMCP("local-valkey")
addresses = [NodeAddress("localhost", 6379)]
glide_client = GlideClient.create(GlideClientConfiguration(addresses))


class User:
    def __init__(self, id, first_name, last_name, is_married):
        self.id = id
        self.first_name = first_name
        self.last_name = last_name
        self.is_married = is_married


class MarriageCertificate(BaseModel):
    shared_last_name: str = Field()


@mcp.tool()
async def generate_user_id() -> str:
    """Generates a new random user id."""
    return uuid.uuid4().hex.upper()[0:8]


@mcp.tool()
def create_user(user_id: str, first_name: str, last_name: str, is_married: bool) -> str:
    """Creates a user entry with a given name and married status. It will fail if a user with the same id already exists.

    Args:
        user_id: the unique id for this user, respecting the format returned by generate_user_id.
        first_name: the first name of the user
        last_name: the last name of the user
        is_married: whether the user is legally married or not
    """
    if glide_client.exists([user_id]) == 1:
        return f"User with id {user_id} already exists, you should update it rather than creating it again."

    glide_client.hset(user_id, {"first_name": first_name, "last_name": last_name, "is_married": str(is_married)})
    return "User successfully created!"


@mcp.tool()
def update_user(
        user_id: str,
        first_name: Optional[str] = None,
        last_name: Optional[str] = None,
        is_married: Optional[bool] = None
) -> str:
    """Updates a user entry with a given name and married status. It will fail if no user with the given id exists.

    Args:
        user_id: the unique id for this user, respecting the format returned by generate_user_id.
        first_name: the first name of the user (optional, if not set, it won't be updated)
        last_name: the last name of the user (optional, if not set, it won't be updated)
        is_married: whether the user is legally married or not (optional, if not set, it won't be updated)
    """
    try:
        return update_user_internal(user_id, first_name, last_name, is_married)
    except Exception as e:
        return str(e)


def update_user_internal(
        user_id: str,
        first_name: Optional[str] = None,
        last_name: Optional[str] = None,
        is_married: Optional[bool] = None
) -> str:
    if glide_client.exists([user_id]) == 0:
        raise Exception(f"User with id {user_id} does not exist, you should create it before being able to modify it.")

    to_update = {}
    if first_name:
        to_update['first_name'] = first_name

    if last_name:
        to_update['last_name'] = last_name

    if is_married is not None:
        to_update['is_married'] = str(is_married)

    if len(to_update) == 0:
        return "Nothing to update, please provide updatable properties if the user needs to be updated"

    glide_client.hset(user_id, to_update)
    return "User successfully updated!"

@mcp.tool()
def get_user(user_id: str) -> str:
    """Get user metadata given a user id.

       Args:
           user_id: the unique id for this user, respecting the format returned by generate_user_id.
    """
    try:
        user = get_user_internal(user_id)
        return f"User with id {user_id} is named {user.first_name} and their last name is {user.last_name}. They are{'' if user.is_married else ' not'} married."
    except Exception as e:
        return str(e)


@mcp.tool()
async def marry(user_id_1: str, user_id_2: str, ctx: Context) -> str:
    """Marry two users with one another. Careful, the priest is quiet fierce. Both users need to be unmarried or they'll get heavily scolded.

    Args:
        user_id_1: the id of the first user to marry
        user_id_2: the id of the second user to marry 
    """
    user_1 = get_user_internal(user_id_1)
    user_2 = get_user_internal(user_id_2)

    if user_1.is_married and user_2.is_married:
        return 'Either you guys are married together, or you are cheating on your respective spouse. Either way, you have no business here.'
    elif user_1.is_married or user_2.is_married:
        cheater = user_1 if user_1.is_married else user_2
        return f'You dirty pig {cheater.first_name}! Go back to your spouse and apologize for being unfaithful!'
    else:
        response = await ctx.elicit(
            message='What is going to be your shared last name after this union?',
            schema=MarriageCertificate
        )

        match response:
            case AcceptedElicitation(data=data):
               shared_last_name = data.shared_last_name 
               update_user_internal(user_id_1, last_name=shared_last_name, is_married=True)   
               update_user_internal(user_id_2, last_name=shared_last_name, is_married=True)
               return 'I hereby declare you united by the ties of marriage. Until you divorce within the year, that is.'
            case DeclinedElicitation():
                return "Sorry guys, I cannot marry you without a shared last name"
            case CancelledElicitation():
                return "Getting cold feet, eh? That's ok, I can understand. Get out of my church now though."


def get_user_internal(user_id: str) -> User:
    user_data = glide_client.hgetall(user_id)
    if len(user_data) == 0:
        raise Exception( f"User with id {user_id} does not exist yet")

    first_name = user_data[b'first_name'].decode()
    last_name = user_data[b'last_name'].decode()
    is_married = user_data[b'is_married'].decode() == 'True'
    return User(user_id, first_name, last_name, is_married)


if __name__ == "__main__":
    # Initialize and run the server
    mcp.run(transport='stdio')

/* In this file we have defined the structure of a receiver
 * A receiver is just a FIFO of tokens from which we can put
 * or get events, it has a pointer to its container port
 * and to the remote receiver
 *
 * @author : William Lucas
 */

#ifndef RECEIVER_H_
#define RECEIVER_H_

#include "$ModelName()_types.h"
#include "$ModelName()_IOPort.h"
#include "$ModelName()_Queue.h"

struct Receiver {
	Queue events;
	IOPort * remotePort;
	IOPort * port;
};

void ReceiverSetRemoteReceiver(Receiver * r, IOPort * port, IOPort * remote);
void ReceiverSetRemoteFarReceiver(Receiver ** r, Receiver * remote);
void ReceiverClear(Receiver * r);
Token ReceiverGet(Receiver * r);
bool ReceiverHasToken(Receiver * r);
void ReceiverPut(Receiver * r, Token t) ;

#endif /* RECEIVER_H_ */

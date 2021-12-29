//
// Created by lrj on 2021/12/5.
//

#ifndef S2GEOMETRY_S2XLOG_H
#define S2GEOMETRY_S2XLOG_H

#endif //S2GEOMETRY_S2XLOG_H

extern "C"
{
#include "access/xlogreader.h"
#include "lib/stringinfo.h"
}

extern "C"
{
#define XLOG_S2_PAGE_UPDATE		0x00
#define XLOG_S2_DELETE			0x10	/* delete leaf index tuples for a
											 * page */
#define XLOG_S2_PAGE_REUSE		0x20	/* old page is about to be reused
											 * from FSM */
#define XLOG_S2_PAGE_SPLIT		0x30
/* #define XLOG_S2_INSERT_COMPLETE	 0x40 */	/* not used anymore */
/* #define XLOG_S2_CREATE_INDEX		 0x50 */	/* not used anymore */
#define XLOG_S2_PAGE_DELETE		0x60

/*
* This is what we need to know about page reuse, for hot standby.
*/
typedef struct s2xlogPageReuse
{
    RelFileNode node;
    BlockNumber block;
    FullTransactionId latestRemovedFullXid;
} s2xlogPageReuse;

#define SizeOfs2xlogPageReuse	(offsetof(s2xlogPageReuse, latestRemovedFullXid) + sizeof(FullTransactionId))

void s2XLogPageReuse(Relation rel, BlockNumber blkno, FullTransactionId latestRemovedXid);
}
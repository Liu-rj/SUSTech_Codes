//
// Created by lrj on 2021/12/5.
//

extern "C"
{
#include "s2.h"
#include "s2xlog.h"
#include "access/bufmask.h"
#include "access/heapam_xlog.h"
#include "access/transam.h"
#include "access/xloginsert.h"
#include "access/xlogutils.h"
#include "miscadmin.h"
#include "storage/procarray.h"
#include "utils/memutils.h"
#include "utils/rel.h"
}

extern "C"
{
/*
* Write XLOG record about reuse of a deleted page.
*/
void s2XLogPageReuse(Relation rel, BlockNumber blkno, FullTransactionId latestRemovedXid)
{
    s2xlogPageReuse xlrec_reuse;

    /*
     * Note that we don't res2er the buffer with the record, because this
     * operation doesn't modify the page. This record only exists to provide a
     * conflict point for Hot Standby.
     */

    /* XLOG stuff */
    xlrec_reuse.node = rel->rd_node;
    xlrec_reuse.block = blkno;
    xlrec_reuse.latestRemovedFullXid = latestRemovedXid;

    XLogBeginInsert();
    XLogRegisterData((char *) &xlrec_reuse, SizeOfs2xlogPageReuse);

    XLogInsert(RM_NEXT_ID, XLOG_S2_PAGE_REUSE);
}
}
import React, { useState, useEffect } from 'react';
import DashboardCard from '../../../components/shared/DashboardCard';
import {
  Timeline,
  TimelineItem,
  TimelineOppositeContent,
  TimelineSeparator,
  TimelineDot,
  TimelineConnector,
  TimelineContent,
  timelineOppositeContentClasses,
} from '@mui/lab';
import { Chip, Typography } from '@mui/material';
import { retrieveRecentWorkorders } from '../../../../api/WorkOrdersApiService';
import statusColors from '../../../../statusColors'


const RecentWorkOrders = () => {
  const [workOrders, setWorkOrders] = useState([]);

  useEffect(() => {
    const fetchRecentWorkOrders = async () => {
      try {
        const response = await retrieveRecentWorkorders();
        setWorkOrders(response.data);
      } catch (error) {
        console.error("Failed to fetch recent work orders:", error);
      }
    };

    fetchRecentWorkOrders();
  }, []);

  return (
    <DashboardCard title="Recent Actions">
      <Timeline
        className="theme-timeline"
        sx={{
          p: 0,
          mb: '-40px',
          '& .MuiTimelineConnector-root': {
            width: '1px',
            backgroundColor: '#efefef',
          },
          [`& .${timelineOppositeContentClasses.root}`]: {
            flex: 0.2,
            paddingLeft: 0,
          },
        }}
      >
        {workOrders.map((order) => (
          <TimelineItem key={order.orderId}>
            <TimelineOppositeContent>
              {new Date(order.lastModificationTimeStamp).toLocaleTimeString()}
            </TimelineOppositeContent>
            <TimelineSeparator>
              <TimelineDot color="primary" variant="outlined" />
              <TimelineConnector />
            </TimelineSeparator>
            <TimelineContent>
              <Typography component="span">
                <span style={{ fontWeight: 400 }}>Work Order </span>
                <span style={{ fontWeight: 600 }}>{order.orderName}</span>
                <span style={{ fontWeight: 400 }}> for </span>
                <span style={{ fontWeight: 600 }}>{order.orderType.price} zł </span>
                changed status to
                <Chip
                  sx={{
                    ml: 1,
                    px: "4px",
                    backgroundColor: statusColors[order.status],
                    color: "#fff"
                  }}
                  size="small"
                  label={order.status}
                />
              </Typography>

            </TimelineContent>
          </TimelineItem>
        ))}
      </Timeline>
    </DashboardCard>
  );
};

export default RecentWorkOrders;

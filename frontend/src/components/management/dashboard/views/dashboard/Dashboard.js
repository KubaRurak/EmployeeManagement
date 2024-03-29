import React from 'react';
import { Grid, Box } from '@mui/material';
import PageContainer from '../../components/container/PageContainer';

// components
import PerformanceOverview from './components/PerformanceOverview';
import YearlyBreakup from './components/YearlyBreakup';
import RecentTransactions from './components/RecentTransactions';
import ProductPerformance from './components/ProductPerformance';
import MonthlyEarnings from './components/MonthlyEarnings';
import WorkOrdersCard from './components/WorkOrdersCard';
import TotalProfitCard from './components/TotalProfitCard';
import UnassignedWorkOrdersCard from './components/UnassignedWorkOrderCard';
import EmployeesCard from './components/EmployeesCard';

const Dashboard = () => {
  return (
    <PageContainer title="Dashboard" description="this is Dashboard">
      <Box>
        <Grid container spacing={3}>
          <Grid item xs={12} lg={3}>
            <WorkOrdersCard />
          </Grid>
          <Grid item xs={12} lg={3}>
            <UnassignedWorkOrdersCard />
          </Grid>
          <Grid item xs={12} lg={3}>
            <EmployeesCard />
          </Grid>
          <Grid item xs={12} lg={3}>
            <TotalProfitCard />
          </Grid>
          <Grid item xs={12} lg={8}>
            <PerformanceOverview />
          </Grid>
          <Grid item xs={12} lg={4}>
            <Grid container spacing={3}>
              <Grid item xs={12}>
                <YearlyBreakup />
              </Grid>
              <Grid item xs={12}>
                <MonthlyEarnings />
              </Grid>
            </Grid>
          </Grid>
          <Grid item xs={12} lg={6}>
            <RecentTransactions />
          </Grid>
          <Grid item xs={12} lg={6}>
            <ProductPerformance />
          </Grid>
        </Grid>
      </Box>
    </PageContainer>
  );
};

export default Dashboard;

import React, { useState, useEffect } from 'react';
import Chart from 'react-apexcharts';
import { useTheme } from '@mui/material/styles';
import { Grid, Stack, Typography, Avatar } from '@mui/material';
import { IconArrowUpLeft } from '@tabler/icons-react';
import { retrieveProfitByOrderType } from '../../../../api/WorkOrdersApiService';

import DashboardCard from '../../../components/shared/DashboardCard';

const YearlyBreakup = () => {
  // chart color
  const theme = useTheme();
  const successlight = theme.palette.success.light;

  
  const [profitData, setProfitData] = useState([]);
  const [mostProfitable, setMostProfitable] = useState({});

  useEffect(() => {
    retrieveProfitByOrderType().then(response => {
      const data = response.data;
      setProfitData(data);
      console.log(data);

      if (data.length) {
        const sortedData = [...data].sort((a, b) => b.totalProfit - a.totalProfit);
        setMostProfitable(sortedData[0]);
      }
    }).catch(error => {
      console.error("Failed to fetch profit data", error);
    });
  }, []);
  const orderTypeNames = profitData.map(item => item.orderTypeName);

  // chart
  const optionscolumnchart = {
    chart: {
      type: 'donut',
      fontFamily: "'Plus Jakarta Sans', sans-serif;",
      foreColor: '#adb0bb',
      toolbar: {
        show: false,
      },
      height: 155,
    },
    colors: ['#FF6633', '#FFB399', '#FF33FF', '#FFFF99', '#00B3E6', '#E6B333', '#3366E6', '#999966', '#99E6E6', '#66998D'],
    plotOptions: {
      pie: {
        startAngle: 0,
        endAngle: 360,
        donut: {
          size: '75%',
          background: 'transparent',
        },
      },
    },
    labels: orderTypeNames,
    tooltip: {
      theme: theme.palette.mode === 'dark' ? 'dark' : 'light',
      fillSeriesColor: true,
      y: {
        formatter: function (val) {
          return val + " zł";
        }
      }
    },
    stroke: {
      show: false,
    },
    dataLabels: {
      enabled: false,
    },
    legend: {
      show: false,
    },
    responsive: [
      {
        breakpoint: 991,
        options: {
          chart: {
            width: 120,
          },
        },
      },
    ],
  };

  const seriescolumnchart = profitData.map(item => item.totalProfit);

  return (
    <DashboardCard title="Profit By Order Type">
      <Grid container spacing={3}>
        {/* column */}
        <Grid item xs={7} sm={7}>
          <Typography variant="h4" fontWeight="600">
            Most profitable {mostProfitable.orderTypeName}
          </Typography>
          <Stack direction="row" spacing={1} mt={1} alignItems="center">
            <Avatar sx={{ bgcolor: "#FF6633", width: 27, height: 27 }}>
              <IconArrowUpLeft width={20} color="#FF6633" />
            </Avatar>
            <Typography variant="subtitle2" fontWeight="600">
              {mostProfitable.totalProfit} zł
            </Typography>
          </Stack>
          <Stack spacing={3} mt={5} direction="row">
            <Stack direction="row" spacing={1} alignItems="center">
            </Stack>
          </Stack>
        </Grid>
        {/* column */}
        <Grid item xs={5} sm={5}>
          <Chart
            options={optionscolumnchart}
            series={seriescolumnchart}
            type="donut"
            height="160px"
          />
        </Grid>
      </Grid>
    </DashboardCard>
  );
};

export default YearlyBreakup;

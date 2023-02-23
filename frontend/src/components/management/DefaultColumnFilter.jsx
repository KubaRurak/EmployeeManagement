function DefaultColumnFilter({
    column: { filterValue, preFilteredRows, setFilter },
}) {
    // const count = preFilteredRows.length

    return (
        <div style={{ display: 'flex', justifyContent: 'center' }}>
            <input
                className="form-control"
                value={filterValue || ''}
                onChange={e => {
                    setFilter(e.target.value || undefined)
                }}
                placeholder={`Filter`}
                // style={{ width: '100px', height: '35px'}}
            />
        </div>
    )
}

export default DefaultColumnFilter
